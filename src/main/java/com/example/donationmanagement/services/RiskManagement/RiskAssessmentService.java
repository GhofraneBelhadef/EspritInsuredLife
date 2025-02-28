package com.example.donationmanagement.services.RiskManagement;
import com.example.donationmanagement.controllers.RiskManagement.RiskAssessmentController;
import com.example.donationmanagement.entities.RiskManagement.RiskAssessment;
import com.example.donationmanagement.entities.RiskManagement.RiskFactors;
import com.example.donationmanagement.repositories.RiskManagement.RiskFactorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.donationmanagement.repositories.RiskManagement.RiskAssessmentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RiskAssessmentService implements IRiskAssessmentService {

    private final RiskAssessmentRepository riskAssessmentRepository;
    private final RiskFactorsRepository riskFactorsRepository;
    @Autowired
    private RiskFactorHistoryService riskFactorHistoryService;
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

    @Transactional
    public RiskAssessment createRiskAssessment(Long userId, List<Long> riskFactorIds) {
        RiskAssessment riskAssessment = new RiskAssessment();
        riskAssessment.setUserId(userId);

        // 🔹 Récupération des RiskFactors à partir des IDs fournis
        List<RiskFactors> riskFactors = riskFactorsRepository.findAllById(riskFactorIds);
        riskAssessment.setRiskFactors(riskFactors);

        // 🔹 Sauvegarde initiale
        riskAssessment = riskAssessmentRepository.save(riskAssessment);

        // 🔹 Calcul et mise à jour automatique
        calculateRiskScore(riskAssessment.getAssessmentId());
        calculatePrice(riskAssessment.getAssessmentId());

        return riskAssessmentRepository.findById(riskAssessment.getAssessmentId()).orElseThrow();
    }
    @Transactional
    public RiskAssessment updateRiskAssessment(Long riskAssessmentId, List<Long> addRiskFactorIds, List<Long> removeRiskFactorIds) {
        RiskAssessment riskAssessment = riskAssessmentRepository.findById(riskAssessmentId)
                .orElseThrow(() -> new RuntimeException("RiskAssessment non trouvé !"));

        List<RiskFactors> currentRiskFactors = new ArrayList<>(riskAssessment.getRiskFactors());

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
        calculatePrice(riskAssessmentId);

        return riskAssessment;
    }

}