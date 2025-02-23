package com.example.donationmanagement.services.LoanManagement;

import com.example.donationmanagement.entities.LoanManagement.RiskBreakdown;
import com.example.donationmanagement.repositories.LoanManagement.RiskBreakdownRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RiskBreakdownService {

    private final RiskBreakdownRepository riskBreakdownRepository;

    public RiskBreakdownService(RiskBreakdownRepository riskBreakdownRepository) {
        this.riskBreakdownRepository = riskBreakdownRepository;
    }

    public List<RiskBreakdown> getAllRiskBreakdowns() {
        return riskBreakdownRepository.findAll();
    }

    public RiskBreakdown getRiskBreakdownById(Long id) {
        return riskBreakdownRepository.findById(id).orElse(null);
    }

    public RiskBreakdown createRiskBreakdown(RiskBreakdown riskBreakdown) {
        return riskBreakdownRepository.save(riskBreakdown);
    }

    public RiskBreakdown updateRiskBreakdown(Long id, RiskBreakdown updatedRiskBreakdown) {
        Optional<RiskBreakdown> existingRiskBreakdown = riskBreakdownRepository.findById(id);
        if (existingRiskBreakdown.isPresent()) {
            RiskBreakdown riskBreakdown = existingRiskBreakdown.get();
            riskBreakdown.setRiskScore(updatedRiskBreakdown.getRiskScore());
            riskBreakdown.setRiskProbability(updatedRiskBreakdown.getRiskProbability());
            riskBreakdown.setCoefficient(updatedRiskBreakdown.getCoefficient());
            riskBreakdown.setFeatureValue(updatedRiskBreakdown.getFeatureValue());
            return riskBreakdownRepository.save(riskBreakdown);
        }
        return null;
    }

    public void deleteRiskBreakdown(Long id) {
        riskBreakdownRepository.deleteById(id);
    }
}
