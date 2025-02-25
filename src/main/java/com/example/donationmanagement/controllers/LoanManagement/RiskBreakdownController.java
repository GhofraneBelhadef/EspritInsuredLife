package com.example.donationmanagement.controllers.LoanManagement;

import com.example.donationmanagement.entities.LoanManagement.RiskBreakdown;
import com.example.donationmanagement.services.LoanManagement.RiskBreakdownService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/riskBreakdown")
public class RiskBreakdownController {

    private final RiskBreakdownService riskBreakdownService;

    public RiskBreakdownController(RiskBreakdownService riskBreakdownService) {
        this.riskBreakdownService = riskBreakdownService;
    }

    // Get all risk breakdowns
    @GetMapping
    public ResponseEntity<List<RiskBreakdown>> getAllRiskBreakdowns() {
        List<RiskBreakdown> riskBreakdowns = riskBreakdownService.getAllRiskBreakdowns();
        return ResponseEntity.ok(riskBreakdowns);
    }

    // Get risk breakdown by ID
    @GetMapping("/{id}")
    public ResponseEntity<RiskBreakdown> getRiskBreakdownById(@PathVariable Long id) {
        RiskBreakdown riskBreakdown = riskBreakdownService.getRiskBreakdownById(id);
        return ResponseEntity.ok(riskBreakdown);
    }

    // Create a new risk breakdown
    @PostMapping("/create")
    public ResponseEntity<RiskBreakdown> createRiskBreakdown(@RequestBody RiskBreakdown riskBreakdown) {
        RiskBreakdown savedRiskBreakdown = riskBreakdownService.createRiskBreakdown(riskBreakdown);
        return ResponseEntity.ok(savedRiskBreakdown);
    }

    // Update an existing risk breakdown
    @PutMapping("/{id}")
    public ResponseEntity<RiskBreakdown> updateRiskBreakdown(@PathVariable Long id, @RequestBody RiskBreakdown riskBreakdown) {
        RiskBreakdown updatedRiskBreakdown = riskBreakdownService.updateRiskBreakdown(id, riskBreakdown);
        return ResponseEntity.ok(updatedRiskBreakdown);
    }

    // Delete a risk breakdown
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRiskBreakdown(@PathVariable Long id) {
        riskBreakdownService.deleteRiskBreakdown(id);
        return ResponseEntity.noContent().build();
    }
}
