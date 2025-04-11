package com.example.donationmanagement.services.ClaimManagement;
import com.example.donationmanagement.config.AppConfig;
import com.example.donationmanagement.entities.ClaimManagement.Notification;
import com.example.donationmanagement.entities.ClaimManagement.Complaint;
import com.example.donationmanagement.repositories.ClaimManagement.ComplaintRepository;
import com.example.donationmanagement.repositories.ClaimManagement.NotificationRepository;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class ComplaintService {
    private final RestTemplate restTemplate;
    private final String predictionApiUrl = "http://localhost:5000/prediction";
    private final ComplaintRepository complaintRepository;
    private final NotificationRepository notificationRepository;

    public ComplaintService(ComplaintRepository complaintRepository, NotificationRepository notificationRepository, RestTemplate restTemplate) {
        this.complaintRepository = complaintRepository;
        this.notificationRepository = notificationRepository;
        this.restTemplate = restTemplate;
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Complaint getComplaintById(Long id) {
        return complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found with id: " + id));
    }


    public Complaint createComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    public Complaint updateComplaint(Long id, Complaint complaintDetails) {
        Complaint complaint = getComplaintById(id);
        if (complaint != null) {
            complaint.setDescription(complaintDetails.getDescription());
            complaint.setStatus(complaintDetails.getStatus());
            complaint.setContractId(complaintDetails.getContractId());
            complaint.setClaimAmount(complaintDetails.getClaimAmount());
            return complaintRepository.save(complaint);
        }
        return null;
    }

    public void deleteComplaint(Long id) {
        if (!complaintRepository.existsById(id)) {
            throw new RuntimeException("Complaint not found with id: " + id);
        }
        complaintRepository.deleteById(id);
    }
    @Scheduled(cron = "0 0 12 * * ?")
    public void sendReminderForPendingComplaints() {
        List<Complaint> pendingComplaints = complaintRepository.findByStatus("OPEN");
        for (Complaint complaint : pendingComplaints) {
            Notification notification = new Notification();
            notification.setMessage("Your complaint #" + complaint.getId() + " is still pending.");
            notification.setTimestamp(LocalDateTime.now());
            notification.setComplaint(complaint);
            notification.setRead(false);
            notificationRepository.save(notification);
        }
    }
    public Page<Complaint> getComplaints(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return complaintRepository.findAll(pageable);
    }
    public Object getPrediction(Object[] features) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Object[]> requestEntity = new HttpEntity<>(features, headers);

            ResponseEntity<Object> response = restTemplate.exchange(
                    "http://127.0.0.1:5000/predict",
                    HttpMethod.POST,
                    requestEntity,
                    Object.class
            );

            return response.getBody();
        } catch (Exception e) {
            System.out.println("Error while fetching prediction: " + e.getMessage());
            return "Error while getting prediction: " + e.getMessage();
        }
    }
    public String recommendSolution(Long complaintId) {
        try {
            // Vérifier si la réclamation existe
            Complaint complaint = getComplaintById(complaintId);
            if (complaint == null) {
                throw new RuntimeException("Réclamation introuvable avec l'ID : " + complaintId);
            }

            // URL de l'API Flask avec complaintId dans l'URL
            String flaskUrl = "http://localhost:5000/recommend/" + complaintId;

            // Définir les en-têtes
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Créer une requête vide, car Flask prend l'ID dans l'URL
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);

            // Envoi de la requête au service Flask
            ResponseEntity<Map> response = restTemplate.exchange(
                    flaskUrl, HttpMethod.POST, requestEntity, Map.class);

            // Vérification du statut de la réponse
            if (response.getStatusCode() == HttpStatus.OK) {
                // Extraire la solution recommandée de la réponse
                Map<String, Object> responseBody = response.getBody();
                String recommendedSolution = (String) responseBody.get("recommended_solution");

                if (recommendedSolution != null) {
                    return recommendedSolution;
                } else {
                    throw new RuntimeException("La solution recommandée est absente de la réponse.");
                }
            } else {
                throw new RuntimeException("Échec de la récupération de la solution. Code de statut : " + response.getStatusCode());
            }
        } catch (Exception e) {
            // Gestion des erreurs
            return "Erreur lors de la récupération de la solution : " + e.getMessage();
        }

    }
}