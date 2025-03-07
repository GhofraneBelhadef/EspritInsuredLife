package com.example.donationmanagement.services.RiskManagement;

import com.example.donationmanagement.entities.RiskManagement.RiskAssessment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface IRiskAssessmentService {
    public RiskAssessment getRiskAssessmentById(Long riskAssessmentId);
    List<RiskAssessment> getAllRiskAssessments();
    public void deleteRiskAssessment(Long RiskAssessmentId);
    public RiskAssessment createRiskAssessment(Long UserId, MultipartFile medicalRecord , List<Long> riskFactorIds, String userWhatsapp) throws IOException;
    public BigDecimal calculatePrice(Long riskAssessmentId);
    public Double calculateRiskScore(Long riskAssessmentId);
    public RiskAssessment updateRiskAssessment(Long riskAssessmentId, List<Long> addRiskFactorIds, List<Long> removeRiskFactorIds);
}