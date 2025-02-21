package com.example.donationmanagement.services.RiskManagement;

import com.example.donationmanagement.entities.RiskManagement.RiskFactors;

import java.util.List;

public interface IRiskFactorsService {
    List<RiskFactors> getAllRiskFactors();
    public RiskFactors updateRiskFactor(Long RiskFactorsId, RiskFactors newRiskFactor);
    public RiskFactors addRiskFactor(RiskFactors riskFactor);
}
