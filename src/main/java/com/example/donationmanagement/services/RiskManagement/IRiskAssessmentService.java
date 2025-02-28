package com.example.donationmanagement.services.RiskManagement;

import com.example.donationmanagement.entities.RiskManagement.RiskAssessment;

import java.math.BigDecimal;
import java.util.List;

public interface IRiskAssessmentService {
    public RiskAssessment getRiskAssessmentById(Long riskAssessmentId);
    List<RiskAssessment> getAllRiskAssessments();
    public void deleteRiskAssessment(Long RiskAssessmentId);
    RiskAssessment createRiskAssessment(Long userId, List<Long> riskFactorIds);
    public BigDecimal calculatePrice(Long riskAssessmentId);
    public Double calculateRiskScore(Long riskAssessmentId);
    public RiskAssessment updateRiskAssessment(Long riskAssessmentId, List<Long> addRiskFactorIds, List<Long> removeRiskFactorIds);
}
