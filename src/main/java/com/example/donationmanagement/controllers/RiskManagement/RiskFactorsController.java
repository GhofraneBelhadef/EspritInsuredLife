package com.example.donationmanagement.controllers.RiskManagement;
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
    @PutMapping("/{id}")
    public RiskFactors updateRiskFactor(@PathVariable Long id, @RequestBody RiskFactors riskFactor) {
        return RiskFactorsService.updateRiskFactor(id, riskFactor);
    }
    @PostMapping
    public RiskFactors createRiskFactor(@RequestBody RiskFactors riskFactor) {
        return RiskFactorsService.addRiskFactor(riskFactor);
    }
}
