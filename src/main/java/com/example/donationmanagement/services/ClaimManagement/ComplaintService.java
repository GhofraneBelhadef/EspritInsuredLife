package com.example.donationmanagement.services.ClaimManagement;

import com.example.donationmanagement.entities.ClaimManagement.Notification;
import com.example.donationmanagement.entities.ClaimManagement.Complaint;
import com.example.donationmanagement.entities.UserManagement.User;
import com.example.donationmanagement.repositories.ClaimManagement.ComplaintRepository;
import com.example.donationmanagement.repositories.ClaimManagement.NotificationRepository;
import com.example.donationmanagement.repositories.UserManagement.UserRepository;
import com.example.donationmanagement.services.UserManagement.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class ComplaintService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    private final String predictionApiUrl = "http://localhost:5000/prediction";
    private final ComplaintRepository complaintRepository;
    private final NotificationRepository notificationRepository;

    public ComplaintService(ComplaintRepository complaintRepository, NotificationRepository notificationRepository) {
        this.complaintRepository = complaintRepository;
        this.notificationRepository = notificationRepository;
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Complaint getComplaintById(Long id) {
        return complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found with id: " + id));
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");  // Récupérer l'en-tête Authorization
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);  // Extraire le token après le mot "Bearer "
        }
        return null;  // Si le token n'est pas présent ou ne commence pas par "Bearer "
    }
    public Optional<User> getAuthenticatedUser(HttpServletRequest request) {
        String token = extractToken(request);
        if (token != null) {
            Long userId = jwtService.extractUserId(token);
            return userRepository.findById(userId); // Recherche l'utilisateur par ID
        }
        return Optional.empty();
    }
    public Complaint createComplaint(Complaint complaint, HttpServletRequest request) {
        // Récupérer l'utilisateur authentifié
        Optional<User> authenticatedUser = getAuthenticatedUser(request);
        if (authenticatedUser.isEmpty()) {
            throw new RuntimeException("Utilisateur non authentifié !");
        }
        User user = authenticatedUser.get();
        complaint.setUser(user);
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

}