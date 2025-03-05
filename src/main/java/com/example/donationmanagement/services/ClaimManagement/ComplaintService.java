package com.example.donationmanagement.services.ClaimManagement;
import com.example.donationmanagement.config.AppConfig;
import com.example.donationmanagement.entities.ClaimManagement.Notification;
import com.example.donationmanagement.entities.ClaimManagement.Complaint;
import com.example.donationmanagement.repositories.ClaimManagement.ComplaintRepository;
import com.example.donationmanagement.repositories.ClaimManagement.NotificationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Object getPrediction(List<Object> features) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("features", features);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(predictionApiUrl, requestEntity, Map.class);
        return response.getBody().get("prediction");
    }

}