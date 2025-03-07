package com.example.donationmanagement.controllers.RiskManagement;
import com.example.donationmanagement.services.RiskManagement.RiskAnalysisService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/risk")
@Validated
public class RiskAnalysisController {

    private final RiskAnalysisService riskAnalysisService;

    public RiskAnalysisController(RiskAnalysisService riskAnalysisService) {
        this.riskAnalysisService = riskAnalysisService;
    }

    @GetMapping("/analyse")
    public List<Map<String, Object>> getRiskAnalysis() {
        return riskAnalysisService.getRiskAnalysis();
    }
}
