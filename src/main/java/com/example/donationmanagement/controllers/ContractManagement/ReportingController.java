
package com.example.donationmanagement.controllers.ContractManagement;
import com.example.donationmanagement.services.ContractManagement.ExcelReportingService;
import com.example.donationmanagement.services.ContractManagement.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportingController {

    private final ReportingService reportingService;
    private final ExcelReportingService excelReportService;

    @Autowired
    public ReportingController(ReportingService reportingService, ExcelReportingService excelReportService) {
        this.reportingService = reportingService;
        this.excelReportService = excelReportService;
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

    @GetMapping("/download/excel")
    public ResponseEntity<byte[]> downloadExcelReport() {
        try {
            byte[] excelData = excelReportService.generateExcelReport();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=report.xlsx");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(excelData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
