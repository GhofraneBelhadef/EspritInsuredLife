package com.example.donationmanagement.controllers.RiskManagement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.donationmanagement.entities.RiskManagement.RiskAssessment;
import com.example.donationmanagement.services.RiskManagement.RiskAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequestMapping("/RiskAssessment")
@Validated
public class RiskAssessmentController {
    @Autowired
    private RiskAssessmentService RiskAssessmentService;
    @GetMapping
    public Page<RiskAssessment> getAllRiskAssessments(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        return RiskAssessmentService.getAllRiskAssessments(page, size);
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
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<RiskAssessment> createRiskAssessment(
            @RequestParam Long userId,
            @RequestParam(required = false) List<Long> riskFactorIds, // Liste des ID de facteurs de risque
            @RequestParam(required = false) String userWhatsapp, // Numéro WhatsApp de l'utilisateur
            @RequestPart(name = "medicalRecord", required = false) MultipartFile medicalRecord) {
        try {
            RiskAssessment riskAssessment = RiskAssessmentService.createRiskAssessment(userId, medicalRecord, riskFactorIds, userWhatsapp);
            return ResponseEntity.ok(riskAssessment);
        } catch (Exception e) {
            e.printStackTrace(); // Affiche l'erreur complète dans la console
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
    @GetMapping("/search")
    public ResponseEntity<List<RiskAssessment>> searchRiskAssessments(@RequestParam String search) {
        return ResponseEntity.ok(RiskAssessmentService.searchRiskAssessments(search));
    }
}
