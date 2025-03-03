package com.example.donationmanagement.services.RiskManagement;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Service
public class RiskAnalysisService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_FLASK_URL = "http://127.0.0.1:5000/analyse_risk"; // URL de ton API Flask

    public List<Map<String, Object>> getRiskAnalysis() {
        // Effectuer la requête GET vers l'API Flask
        return restTemplate.getForObject(API_FLASK_URL, List.class);
    }
}
