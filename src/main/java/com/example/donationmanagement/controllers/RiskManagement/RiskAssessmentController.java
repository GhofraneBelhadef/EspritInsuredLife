package com.example.donationmanagement.controllers.RiskManagement;
import org.springframework.web.bind.annotation.*;
import com.example.donationmanagement.entities.RiskManagement.RiskAssessment;
import com.example.donationmanagement.services.RiskManagement.RiskAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@RestController
@RequestMapping("/RiskAssessment")
public class RiskAssessmentController {
    @Autowired
    private RiskAssessmentService RiskAssessmentService;
    @GetMapping
    public List<RiskAssessment> getAllRiskAssessments() {
        return RiskAssessmentService.getAllRiskAssessments();
    }
    @DeleteMapping("/{id}")
    public String deleteAssessment(@PathVariable Long id) {
        RiskAssessmentService.deleteRiskAssessment(id);
        return "Risk Factor supprimé avec succès";
    }
}
