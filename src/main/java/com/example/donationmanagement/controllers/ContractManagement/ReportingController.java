package com.example.donationmanagement.controllers.ContractManagement;

import com.example.donationmanagement.services.ContractManagement.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportingController {
@Autowired
    private ReportingService reportingService;

    public ReportingController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @GetMapping("/contracts/count")
    public long getTotalContracts() {
        return reportingService.getTotalContracts();
    }

    @GetMapping("/contracts/type-distribution")
    public Map<String, Long> getContractTypeDistribution() {
        return reportingService.getContractTypeDistribution();
    }

    @GetMapping("/contracts/total-capital")
    public float getTotalCapital() {
        return reportingService.getTotalCapital();
    }

    @GetMapping("/provisions/total")
    public float getTotalProvisions() {
        return reportingService.getTotalProvisions();
    }

    @GetMapping("/sinistralite/stats")
    public float getAverageSinistralite() {
        return reportingService.getAverageSinistralite();
    }
}

