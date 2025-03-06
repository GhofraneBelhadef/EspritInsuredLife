package com.example.donationmanagement.services.RiskManagement;
import com.example.donationmanagement.controllers.RiskManagement.RiskAssessmentController;
import com.example.donationmanagement.entities.RiskManagement.RiskAssessment;
import com.example.donationmanagement.repositories.RiskManagement.RiskFactorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.donationmanagement.repositories.RiskManagement.RiskAssessmentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class RiskAssessmentService implements IRiskAssessmentService {
    @Autowired
    private RiskAssessmentRepository RiskAssessmentRepository;

    public List<RiskAssessment> getAllRiskAssessments() {
        return RiskAssessmentRepository.findAll();
    }
    public void deleteRiskAssessment(Long RiskAssessmentId) {
        if (RiskAssessmentRepository.existsById(RiskAssessmentId)) {
            RiskAssessmentRepository.deleteById(RiskAssessmentId);
        } else {
            throw new RuntimeException("Risk Factor not found with ID: " + RiskAssessmentId);
        }
    }
        @Transactional // S'assure que la mise à jour est bien effectuée
        public Double calculateRiskScore(Long RiskAssessmentId) {
            Optional<RiskAssessment> riskAssessmentOpt = RiskAssessmentRepository.findById(RiskAssessmentId);
            if (riskAssessmentOpt.isPresent()) {
                RiskAssessment riskAssessment = riskAssessmentOpt.get();
                // 🔹 Calcul du Risk Score
                Double RiskScore = Math.random() * 100; // Exemple : entre 0 et 100
                // 🔹 Mise à jour automatique du Risk Score
                riskAssessment.setRiskScore(RiskScore);
                RiskAssessmentRepository.save(riskAssessment);  // Sauvegarde automatique
                return RiskScore;
            }
            throw new RuntimeException("RiskAssessment non trouvé !");
        }
    @Transactional
    public BigDecimal calculatePrice(Long riskAssessmentId) {
        Optional<RiskAssessment> riskAssessmentOpt = RiskAssessmentRepository.findById(riskAssessmentId);

        if (riskAssessmentOpt.isPresent()) {
            RiskAssessment riskAssessment = riskAssessmentOpt.get();
            // 🔹 Vérifier que le Risk Score est déjà calculé
            if (riskAssessment.getRiskScore() == null) {
                throw new RuntimeException("Risk Score non encore calculé !");
            }
            // 🔹 Calcul automatique du prix
            BigDecimal Price = BigDecimal.valueOf(riskAssessment.getRiskScore() * 10); // Exemple : 10 unités monétaires par point
            // 🔹 Mise à jour automatique du Prix
            riskAssessment.setPrice(Price);
            RiskAssessmentRepository.save(riskAssessment);  // Sauvegarde automatique
            return Price;
        }
        throw new RuntimeException("RiskAssessment non trouvé !");
    }
    @Transactional
    public RiskAssessment createRiskAssessment(Long UserId) {
        RiskAssessment RiskAssessment = new RiskAssessment();
        RiskAssessment.setUserId(UserId);
        // 🔹 Sauvegarde initiale sans Risk Score ni Prix
        RiskAssessment = RiskAssessmentRepository.save(RiskAssessment);
        // 🔹 Calcul et mise à jour automatique
        Double riskScore = calculateRiskScore(RiskAssessment.getAssessmentId());
        BigDecimal price = calculatePrice(RiskAssessment.getAssessmentId());

        return RiskAssessment;  // Retourne l'objet mis à jour
    }
}


