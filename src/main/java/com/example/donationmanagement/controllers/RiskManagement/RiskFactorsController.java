package com.example.donationmanagement.controllers.RiskManagement;
import com.example.donationmanagement.dto.RiskFactorsDTO;
import com.example.donationmanagement.entities.RiskManagement.RiskAssessment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.donationmanagement.entities.RiskManagement.RiskFactors;
import com.example.donationmanagement.services.RiskManagement.RiskFactorsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@RestController
@RequestMapping("/RiskFactors")
@Validated
public class RiskFactorsController {
    @Autowired
    private RiskFactorsService RiskFactorsService;
    @GetMapping
    public Page<RiskFactors> getAllRiskFactors(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        return RiskFactorsService.getAllRiskFactors(page, size);
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
            @RequestParam
            @Min(value = 1, message = "La valeur du facteur ne peut pas être négative ou null")
            @Max(value = 100, message = "La valeur du facteur ne doit pas dépasser 100")
            int factorValue,
            @RequestParam @NotNull(message = "La description ne doit pas être nulle") String description)  {
        try{ RiskFactors riskFactors = RiskFactorsService.createRiskFactors(factorType, factorValue, description);
            return ResponseEntity.ok(riskFactors);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
