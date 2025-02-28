package com.example.donationmanagement.controllers.RiskManagement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/{id}")
    public ResponseEntity<RiskAssessment> getRiskAssessmentById(@PathVariable Long id) {
        RiskAssessment riskAssessment = RiskAssessmentService.getRiskAssessmentById(id);
        return ResponseEntity.ok(riskAssessment);
    }
    @PostMapping
    public ResponseEntity<RiskAssessment> createRiskAssessment(
            @RequestParam Long userId,
            @RequestParam List<Long> riskFactorIds)  {
        try{ RiskAssessment riskAssessment = RiskAssessmentService.createRiskAssessment(userId, riskFactorIds);
        return ResponseEntity.ok(riskAssessment);
    } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<RiskAssessment> updateRiskAssessment(
            @PathVariable Long id,
            @RequestParam(required = false) List<Long> addRiskFactorIds,
            @RequestParam(required = false) List<Long> removeRiskFactorIds) {
        return ResponseEntity.ok(RiskAssessmentService.updateRiskAssessment(id, addRiskFactorIds, removeRiskFactorIds));
    }
}
