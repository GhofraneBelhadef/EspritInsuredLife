package com.example.donationmanagement.services.RiskManagement;

import com.example.donationmanagement.entities.RiskManagement.RiskAssessment;

import java.util.List;

public interface IRiskAssessmentService {
    List<RiskAssessment> getAllRiskAssessments();
    public void deleteRiskAssessment(Long RiskAssessmentId);
}
