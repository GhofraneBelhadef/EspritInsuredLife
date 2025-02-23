package com.example.donationmanagement.services.RiskManagement;
import com.example.donationmanagement.controllers.RiskManagement.RiskAssessmentController;
import com.example.donationmanagement.entities.RiskManagement.RiskAssessment;
import com.example.donationmanagement.repositories.RiskManagement.RiskFactorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.donationmanagement.repositories.RiskManagement.RiskAssessmentRepository;

import java.util.List;

@Service
public class RiskAssessmentService implements IRiskAssessmentService {
    @Autowired
    private RiskAssessmentRepository RiskAssessmentRepository;

    public List<RiskAssessment> getAllRiskAssessments() {
        return RiskAssessmentRepository.findAll();
    }
    public void deleteRiskAssessment(Long RiskAssessmentId) {
        if (RiskAssessmentRepository.existsById(RiskAssessmentId)) {
            RiskAssessmentRepository.deleteById(RiskAssessmentId);
        } else {
            throw new RuntimeException("Risk Factor not found with ID: " + RiskAssessmentId);
        }
    }
}
