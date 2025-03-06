package com.example.donationmanagement.services.RiskManagement;

import com.example.donationmanagement.entities.RiskManagement.RiskAssessment;

import java.math.BigDecimal;
import java.util.List;

public interface IRiskAssessmentService {
    List<RiskAssessment> getAllRiskAssessments();
    public void deleteRiskAssessment(Long RiskAssessmentId);
    public RiskAssessment createRiskAssessment(Long UserId);
    public BigDecimal calculatePrice(Long riskAssessmentId);
    public Double calculateRiskScore(Long RiskAssessmentId);
}
