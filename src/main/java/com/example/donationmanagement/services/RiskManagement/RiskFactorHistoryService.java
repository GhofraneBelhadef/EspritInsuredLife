package com.example.donationmanagement.services.RiskManagement;

import com.example.donationmanagement.entities.RiskManagement.RiskAssessment;
import com.example.donationmanagement.entities.RiskManagement.RiskFactorHistory;
import com.example.donationmanagement.repositories.RiskManagement.RiskAssessmentRepository;
import com.example.donationmanagement.repositories.RiskManagement.RiskFactorHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class RiskFactorHistoryService {

    @Autowired
    private RiskFactorHistoryRepository riskFactorHistoryRepository;
    @Autowired
    private RiskAssessmentRepository riskAssessmentRepository; // Ajout du repository RiskAssessment

    @Transactional
    public void addRiskFactorHistory(Long riskAssessmentId, Long riskFactorId, int factorValue, String description) {
        RiskAssessment riskAssessment = riskAssessmentRepository.findById(riskAssessmentId)
                .orElseThrow(() -> new RuntimeException("RiskAssessment non trouvé avec ID : " + riskAssessmentId));

        RiskFactorHistory history = new RiskFactorHistory();
        history.setRiskAssessment(riskAssessment); // 🔹 Associer au RiskAssessment
        history.setUserId(riskAssessment.getUserId()); // 🔹 Récupérer le UserId de RiskAssessment
        history.setRiskFactorId(riskFactorId);
        history.setFactorValue(factorValue);
        history.setDescription(description);
        history.setChangeDate(LocalDateTime.now());

        riskFactorHistoryRepository.save(history);
    }
    public List<RiskFactorHistory> getRiskFactorHistory(Long userId) {
        // Récupérer tous les changements des facteurs de risque pour un utilisateur
        return riskFactorHistoryRepository.findByUserId(userId);
    }
}
