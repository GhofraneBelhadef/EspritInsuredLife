package com.example.donationmanagement.entities.RiskManagement;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MedicalDataExtractor {
    // Méthode pour extraire l'âge et les maladies
    public static Map<String, String> extractMedicalInfo(String text) {
        Map<String, String> medicalInfo = new HashMap<>();

        // Extraction de l'âge
        String agePattern = "(?i)(?:age|âge)[^\\d]*(\\d{2})";  // Exemple : "Age: 45" ou "Âge: 45"
        Pattern pattern = Pattern.compile(agePattern);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            medicalInfo.put("Age", matcher.group(1));
        }

        // Extraction des maladies chroniques (exemple avec un mot-clé 'maladie chronique')
        String diseasesPattern = "(?i)(maladie chronique[^\\n]*)";  // Exemple : "Maladie chronique: diabète"
        pattern = Pattern.compile(diseasesPattern);
        matcher = pattern.matcher(text);

        if (matcher.find()) {
            medicalInfo.put("ChronicDisease", matcher.group(1));
        }

        return medicalInfo;
    }
}
