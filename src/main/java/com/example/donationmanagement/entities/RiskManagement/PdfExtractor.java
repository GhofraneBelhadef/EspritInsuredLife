package com.example.donationmanagement.entities.RiskManagement;

import jakarta.persistence.Entity;
import lombok.Data;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PdfExtractor {
    // Méthode pour extraire le texte du PDF
    public String extractTextFromPdf(String filePath) throws IOException {
        PDDocument document = PDDocument.load(new File(filePath));
        PDFTextStripper pdfStripper = new PDFTextStripper();

        // Extraire tout le texte du fichier PDF
        String text = pdfStripper.getText(document);

        // Fermer le document pour libérer la mémoire
        document.close();

        return text;
    }
}
