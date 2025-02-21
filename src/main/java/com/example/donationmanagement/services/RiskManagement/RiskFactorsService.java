package com.example.donationmanagement.services.RiskManagement;

import com.example.donationmanagement.entities.RiskManagement.RiskFactors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.donationmanagement.repositories.RiskManagement.RiskFactorsRepository;

import java.util.List;

@Service
public class RiskFactorsService implements IRiskFactorsService {
    @Autowired
    private RiskFactorsRepository RiskFactorsRepository;
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
    public RiskFactors updateRiskFactor(Long RiskFactorsId, RiskFactors newRiskFactor) {
        return RiskFactorsRepository.findById(RiskFactorsId).map(existingRiskFactor -> {
            existingRiskFactor.setFactorType(newRiskFactor.getFactorType());
            existingRiskFactor.setFactorValue(newRiskFactor.getFactorValue());
            existingRiskFactor.setDescription(newRiskFactor.getDescription());
            return RiskFactorsRepository.save(existingRiskFactor);
        }).orElseThrow(() -> new RuntimeException("Risk Factor not found with ID: " + RiskFactorsId));
    }
    public RiskFactors addRiskFactor(RiskFactors riskFactor) {
        return RiskFactorsRepository.save(riskFactor);
    }
}
