package com.example.donationmanagement.controllers.RiskManagement;
import com.example.donationmanagement.dto.RiskFactorsDTO;
import com.example.donationmanagement.entities.RiskManagement.RiskAssessment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.donationmanagement.entities.RiskManagement.RiskFactors;
import com.example.donationmanagement.services.RiskManagement.RiskFactorsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@RestController
@RequestMapping("/RiskFactors")
public class RiskFactorsController {
    @Autowired
    private RiskFactorsService RiskFactorsService;
    @GetMapping
    public List<RiskFactors> getAllRiskFactors() {
        return RiskFactorsService.getAllRiskFactors();
    }
   @DeleteMapping("/{id}")
   public String deleteRiskFactor(@PathVariable Long id) {
       RiskFactorsService.deleteRiskFactor(id);
       return "Risk Factor supprimé avec succès";
   }
    @GetMapping("/{id}")
    public ResponseEntity<RiskFactors> getRiskFactorsById(@PathVariable Long id) {
        RiskFactors riskFactors = RiskFactorsService.getRiskFactorsById(id);
        return ResponseEntity.ok(riskFactors);
    }
    @PutMapping("/{id}")
    public ResponseEntity<RiskFactors> updateRiskFactors(
            @PathVariable("id") Long id,
            @RequestBody RiskFactorsDTO dto) {

        try {
            RiskFactors updatedRiskFactor = RiskFactorsService.updateRiskFactors(id, dto);
            return new ResponseEntity<>(updatedRiskFactor, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<RiskFactors> createRiskFactors(
            @RequestParam RiskFactors.FactorType factorType,
            @RequestParam int factorValue,
            @RequestParam String description)  {
        try{ RiskFactors riskFactors = RiskFactorsService.createRiskFactors(factorType, factorValue, description);
            return ResponseEntity.ok(riskFactors);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
