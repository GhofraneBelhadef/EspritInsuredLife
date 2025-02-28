package com.example.donationmanagement.controllers.RiskManagement;

import com.example.donationmanagement.entities.RiskManagement.RiskFactorHistory;
import com.example.donationmanagement.services.RiskManagement.RiskFactorHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/risk-factors/history")
public class RiskFactorHistoryController {

    @Autowired
    private RiskFactorHistoryService riskFactorHistoryService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<RiskFactorHistory>> getRiskFactorHistory(@PathVariable Long userId) {
        List<RiskFactorHistory> historyList = riskFactorHistoryService.getRiskFactorHistory(userId);
        if (historyList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(historyList);
    }
}
