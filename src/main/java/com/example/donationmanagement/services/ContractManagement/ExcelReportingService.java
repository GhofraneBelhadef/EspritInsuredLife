package com.example.donationmanagement.services.ContractManagement;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class ExcelReportingService {

    private final ReportingService reportingService;

    public ExcelReportingService(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    public byte[] generateExcelReport() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reporting");

        // 🔹 Style des titres
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        // 🔹 Création des en-têtes
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Statistique", "Valeur"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // 🔹 Récupération des données du reporting
        Map<String, Long> contractDistribution = reportingService.getContractTypeDistribution();
        long lifeInsuranceCount = contractDistribution != null ? contractDistribution.getOrDefault("Life_Insurance", 0L) : 0L;
        long nonLifeInsuranceCount = contractDistribution != null ? contractDistribution.getOrDefault("Non_lifeinsurance", 0L) : 0L;

        Object[][] reportData = {
                {"Nombre total de contrats", reportingService.getTotalContracts()},
                {"Répartition Vie", lifeInsuranceCount},
                {"Répartition Non-Vie", nonLifeInsuranceCount},
                {"Capital total assuré", reportingService.getTotalCapital()},
                {"Total des provisions techniques", reportingService.getTotalProvisions()},
                {"Moyenne des taux de sinistralité", reportingService.getAverageSinistralite()}
        };

        // 🔹 Ajout des données dans le fichier Excel
        int rowIdx = 1;
        for (Object[] rowData : reportData) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(rowData[0].toString());

            if (rowData[1] instanceof Number) {
                row.createCell(1).setCellValue(((Number) rowData[1]).doubleValue());
            } else {
                row.createCell(1).setCellValue(rowData[1].toString());
            }
        }

        // 🔹 Ajustement automatique de la taille des colonnes
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);

        // 🔹 Convertir le fichier Excel en tableau de bytes
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }
}
