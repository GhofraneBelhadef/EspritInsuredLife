package com.example.donationmanagement.services.RiskManagement;

import com.example.donationmanagement.dto.RiskFactorsDTO;
import com.example.donationmanagement.entities.RiskManagement.RiskAssessment;
import com.example.donationmanagement.entities.RiskManagement.RiskFactors;

import com.example.donationmanagement.repositories.RiskManagement.RiskFactorHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.donationmanagement.repositories.RiskManagement.RiskFactorsRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RiskFactorsService implements IRiskFactorsService {
    @Autowired
    private RiskFactorsRepository RiskFactorsRepository;
    @Autowired
    private RiskFactorHistoryService RiskFactorHistoryService;

    @Override
    public List<RiskFactors> getAllRiskFactors() {
        return RiskFactorsRepository.findAll();
    }
    public void deleteRiskFactor(Long RiskFactorsId) {
        if (RiskFactorsRepository.existsById(RiskFactorsId)) {
            RiskFactorsRepository.deleteById(RiskFactorsId);
        } else {
            throw new RuntimeException("Risk Factor not found with ID: " + RiskFactorsId);
        }
    }
    public RiskFactorsDTO getRiskFactorById(Long id) {
        RiskFactors riskFactor = RiskFactorsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Risk Factor not found with ID: " + id));

        // Convertir en DTO
        RiskFactorsDTO dto = new RiskFactorsDTO();
        dto.setFactorValue(riskFactor.getFactorValue());
        dto.setFactorType(riskFactor.getFactorType());
        dto.setDescription(riskFactor.getDescription());
        return dto;
    }

    @Transactional
    public RiskFactors updateRiskFactors(Long id, RiskFactorsDTO dto) {
        RiskFactors existingRiskFactor =    RiskFactorsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Risk Factor not found with ID: " + id));

        // Récupérer l'ID de l'utilisateur associé au facteur de risque
        Long userId = existingRiskFactor.getRiskAssessments().get(0).getUserId();  // Enregistrer l'historique avant la mise à jour
        RiskFactorHistoryService.addRiskFactorHistory(
                userId, // ID de l'utilisateur
                existingRiskFactor.getRiskFactorsId(),
                dto.getFactorValue(), // Nouvelle valeur du facteur
                "Mise à jour du facteur de risque : " + dto.getDescription() // Description du changement
        );
        // Mise à jour des valeurs
        existingRiskFactor.setFactorValue(dto.getFactorValue());
        existingRiskFactor.setFactorType(dto.getFactorType());
        existingRiskFactor.setDescription(dto.getDescription());

        return RiskFactorsRepository.save(existingRiskFactor);
    }
    public RiskFactors getRiskFactorsById(Long riskFactorsId) {
        return RiskFactorsRepository.findById(riskFactorsId)
                .orElseThrow(() -> new RuntimeException("RiskAssessment non trouvé avec ID : " + riskFactorsId));
    }
    @Transactional
    public RiskFactors createRiskFactors(RiskFactors.FactorType factorType, int factorValue, String description) {
        RiskFactors riskFactors = new RiskFactors();
        riskFactors.setFactorType(factorType);
        riskFactors.setFactorValue(factorValue);
        riskFactors.setDescription(description);
        // 🔹 Sauvegarde initiale
        riskFactors = RiskFactorsRepository.save(riskFactors);
        return RiskFactorsRepository.findById(riskFactors.getRiskFactorsId()).orElseThrow();
    }
}
