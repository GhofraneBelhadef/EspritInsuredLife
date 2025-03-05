package com.example.donationmanagement.services.ContractManagement;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class SinistraliteService {
    private Map<String, Float> tauxSinistraliteMap = new HashMap<>();

    public SinistraliteService() {
        chargerTauxSinistralite();
    }

    private void chargerTauxSinistralite() {
        try {
            InputStream inputStream = new ClassPathResource("Data/sinistralite.json").getInputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputStream);

            // Charger les taux dans la Map
            tauxSinistraliteMap.put("Worker", (float) jsonNode.get("Worker").asDouble());
            tauxSinistraliteMap.put("Employee", (float) jsonNode.get("Employee").asDouble());
            tauxSinistraliteMap.put("Business", (float) jsonNode.get("Business").asDouble());
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement du fichier sinistralite.json", e);
        }
    }

    public float getTauxSinistralite(String category) {
        return tauxSinistraliteMap.getOrDefault(category, 0.0f); // Retourne 0 si la catégorie n'existe pas
    }
    public float getAverageSinistralite() {
        if (tauxSinistraliteMap.isEmpty()) {
            return 0.0f; // Éviter la division par zéro
        }
        float somme = 0.0f;
        for (float taux : tauxSinistraliteMap.values()) {
            somme += taux;
        }
        return somme / tauxSinistraliteMap.size();
    }
}
