package com.example.donationmanagement.services.RiskManagement;

import com.example.donationmanagement.dto.RiskFactorsDTO;
import com.example.donationmanagement.entities.RiskManagement.RiskAssessment;
import com.example.donationmanagement.entities.RiskManagement.RiskFactors;

import java.util.List;

public interface IRiskFactorsService {
    List<RiskFactors> getAllRiskFactors();
    public RiskFactors updateRiskFactors(Long id, RiskFactorsDTO dto);
    public RiskFactorsDTO getRiskFactorById(Long id);
    public void deleteRiskFactor(Long RiskFactorsId);
    public RiskFactors getRiskFactorsById(Long riskFactorsId);
    public RiskFactors createRiskFactors(RiskFactors.FactorType factor_type, int factorValue, String description);
}
