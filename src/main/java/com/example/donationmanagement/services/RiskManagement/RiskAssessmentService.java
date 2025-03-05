package com.example.donationmanagement.services.RiskManagement;
import com.example.donationmanagement.controllers.RiskManagement.RiskAssessmentController;
import com.example.donationmanagement.entities.RiskManagement.MedicalDataExtractor;
import com.example.donationmanagement.entities.RiskManagement.PdfExtractor;
import com.example.donationmanagement.entities.RiskManagement.RiskAssessment;
import com.example.donationmanagement.entities.RiskManagement.RiskFactors;
import com.example.donationmanagement.repositories.RiskManagement.RiskFactorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.donationmanagement.repositories.RiskManagement.RiskAssessmentRepository;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class RiskAssessmentService implements IRiskAssessmentService {

    private final RiskAssessmentRepository riskAssessmentRepository;
    private final RiskFactorsRepository riskFactorsRepository;
    private static final String UPLOAD_DIR = "C:/Users/HP/Downloads/";
    @Autowired
    private RiskFactorHistoryService riskFactorHistoryService;
    @Autowired
    private TwilioService twilioService;
    static {
        Path directory = Paths.get(UPLOAD_DIR);
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);  // Crée le répertoire si inexistant
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Autowired
    public RiskAssessmentService(RiskAssessmentRepository riskAssessmentRepository,
                                 RiskFactorsRepository riskFactorsRepository) {
        this.riskAssessmentRepository = riskAssessmentRepository;
        this.riskFactorsRepository = riskFactorsRepository; // ✅ Initialisation correcte
    }
    public RiskAssessment getRiskAssessmentById(Long riskAssessmentId) {
        return riskAssessmentRepository.findById(riskAssessmentId)
                .orElseThrow(() -> new RuntimeException("RiskAssessment non trouvé avec ID : " + riskAssessmentId));
    }


    public List<RiskAssessment> getAllRiskAssessments() {
        return riskAssessmentRepository.findAll();
    }

    public void deleteRiskAssessment(Long riskAssessmentId) {
        if (riskAssessmentRepository.existsById(riskAssessmentId)) {
            riskAssessmentRepository.deleteById(riskAssessmentId);
        } else {
            throw new RuntimeException("Risk Factor not found with ID: " + riskAssessmentId);
        }
    }

    @Transactional
    public Double calculateRiskScore(Long riskAssessmentId) {
        Optional<RiskAssessment> riskAssessmentOpt = riskAssessmentRepository.findById(riskAssessmentId);
        if (riskAssessmentOpt.isPresent()) {
            RiskAssessment riskAssessment = riskAssessmentOpt.get();
            Double oldRiskScore = riskAssessment.getRiskScore(); // 🔹 Récupérer l'ancien score

            // 🔹 Calcul du Risk Score
            Double newRiskScore = (double) riskAssessment.getRiskFactors().stream()
                    .mapToInt(RiskFactors::getFactorValue)
                    .sum();

            riskAssessment.setRiskScore(newRiskScore);
            riskAssessmentRepository.save(riskAssessment);

            // 🔹 Enregistrer la modification dans RiskFactorHistory
            riskFactorHistoryService.addRiskFactorHistory(
                    riskAssessment.getAssessmentId(),
                    null, // Pas un facteur de risque spécifique
                    newRiskScore.intValue(), // Nouvelle valeur
                    "Mise à jour automatique du RiskScore"
            );

            return newRiskScore;
        }
        throw new RuntimeException("RiskAssessment non trouvé !");
    }


    @Transactional
    public BigDecimal calculatePrice(Long riskAssessmentId) {
        Optional<RiskAssessment> riskAssessmentOpt = riskAssessmentRepository.findById(riskAssessmentId);

        if (riskAssessmentOpt.isPresent()) {
            RiskAssessment riskAssessment = riskAssessmentOpt.get();

            // 🔹 Vérifier que le Risk Score est déjà calculé
            if (riskAssessment.getRiskScore() == null) {
                throw new RuntimeException("Risk Score non encore calculé !");
            }

            // 🔹 Calcul du prix
            BigDecimal price = BigDecimal.valueOf(((riskAssessment.getRiskScore() * 0.03)+1)*100)
                    .setScale(2, RoundingMode.HALF_UP);

            // 🔹 Mise à jour et sauvegarde
            riskAssessment.setPrice(price);
            return riskAssessmentRepository.save(riskAssessment).getPrice();
        }
        throw new RuntimeException("RiskAssessment non trouvé !");
    }
    public String uploadMedicalRecord(RiskAssessment riskAssessment, MultipartFile file) throws IOException {
        // Vérifier que c'est un fichier PDF
        if (!file.getContentType().equals("application/pdf")) {
            throw new RuntimeException("Seuls les fichiers PDF sont acceptés.");
        }

        // Enregistrer le fichier PDF
        String fileName = "medical_" + riskAssessment.getAssessmentId() + ".pdf";  // Générer un nom unique pour le fichier
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Files.write(filePath, file.getBytes());
// Extraire le texte du fichier PDF
        PdfExtractor pdfExtractor = new PdfExtractor();
        String text = pdfExtractor.extractTextFromPdf(filePath.toString());

        // Extraire l'âge et les maladies chroniques
        Map<String, String> medicalInfo = MedicalDataExtractor.extractMedicalInfo(text);
        // Mettre à jour l'utilisateur avec ces informations extraites
        if (medicalInfo.containsKey("Age")) {
            riskAssessment.setAge(Integer.parseInt(medicalInfo.get("Age")));
        }
        if (medicalInfo.containsKey("ChronicDisease")) {
            riskAssessment.setChronicDisease(medicalInfo.get("ChronicDisease"));
        }

        // Associer le fichier à l'utilisateur
        riskAssessment.setMedicalRecordPath(filePath.toString());

        // Mise à jour de l'entité existante dans la base de données
        riskAssessmentRepository.save(riskAssessment);

        return fileName;
    }
    @Transactional
    public RiskAssessment createRiskAssessment(Long UserId, MultipartFile medicalRecord) throws IOException {
        RiskAssessment riskAssessment = new RiskAssessment();
        riskAssessment.setUserId(UserId);
        riskAssessment = riskAssessmentRepository.save(riskAssessment);
        // Ajouter le fichier médical (PDF)
        if (medicalRecord != null && !medicalRecord.isEmpty()) {
            uploadMedicalRecord(riskAssessment, medicalRecord);
        }
        // 🔹 Calcul et mise à jour automatique
       // calculateRiskScore(riskAssessment.getAssessmentId());
     //   calculatePrice(riskAssessment.getAssessmentId());
        return riskAssessmentRepository.findById(riskAssessment.getAssessmentId()).orElseThrow();
    }
    @Transactional
    public RiskAssessment updateRiskAssessment(Long riskAssessmentId, List<Long> addRiskFactorIds, List<Long> removeRiskFactorIds) {
        RiskAssessment riskAssessment = riskAssessmentRepository.findById(riskAssessmentId)
                .orElseThrow(() -> new RuntimeException("RiskAssessment non trouvé !"));

        List<RiskFactors> currentRiskFactors = new ArrayList<>(riskAssessment.getRiskFactors());
        Double oldRiskScore = riskAssessment.getRiskScore();
        // Ajout des nouveaux facteurs de risque
        if (addRiskFactorIds != null && !addRiskFactorIds.isEmpty()) {
            List<RiskFactors> newRiskFactors = riskFactorsRepository.findAllById(addRiskFactorIds);
            currentRiskFactors.addAll(newRiskFactors);
        }

        // Suppression des facteurs de risque spécifiés
        if (removeRiskFactorIds != null && !removeRiskFactorIds.isEmpty()) {
            currentRiskFactors.removeIf(rf -> removeRiskFactorIds.contains(rf.getRiskFactorsId()));
        }

        riskAssessment.setRiskFactors(currentRiskFactors);
        riskAssessment = riskAssessmentRepository.save(riskAssessment);

        // Recalculer le score de risque et le prix
        calculateRiskScore(riskAssessmentId);
        Double newRiskScore = riskAssessment.getRiskScore();
        if (newRiskScore < oldRiskScore) {
            String userWhatsapp = riskAssessment.getUserWhatsapp(); // Récupérer le numéro WhatsApp de l'utilisateur
            if (userWhatsapp != null && !userWhatsapp.isEmpty()) {
                // Appeler Twilio pour envoyer un message
                String message = "Félicitations ! Votre RiskScore a amélioré. Continuez à améliorer votre santé.";
                twilioService.sendWhatsappMessage(userWhatsapp, message); // Envoi du message WhatsApp
            }
        }
        calculatePrice(riskAssessmentId);

        return riskAssessment;
    }
    public Page<RiskAssessment> getAllRiskAssessments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return riskAssessmentRepository.findAll(pageable);
    }
    public List<RiskAssessment> searchRiskAssessments(String search) {
        return riskAssessmentRepository.searchRiskAssessments(search);
    }
}