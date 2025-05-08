package com.example.donationmanagement.controllers.ClaimManagement;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.donationmanagement.entities.ClaimManagement.Complaint;
import com.example.donationmanagement.services.ClaimManagement.ComplaintService;
import com.example.donationmanagement.repositories.ClaimManagement.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/complaints")
@CrossOrigin(origins = "http://localhost:4200")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ComplaintRepository complaintRepository;

    @PostMapping
    public ResponseEntity<Complaint> createComplaint(@RequestBody Complaint complaint) {
        Complaint savedComplaint = complaintService.createComplaint(complaint);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComplaint);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Complaint> getComplaintById(@PathVariable Long id) {
        Complaint complaint = complaintService.getComplaintById(id);
        return (complaint != null) ? ResponseEntity.ok(complaint) : ResponseEntity.notFound().build();
    }


    @GetMapping
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        List<Complaint> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok(complaints);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Complaint> updateComplaint(@PathVariable Long id, @RequestBody Complaint complaint) {
        Complaint updatedComplaint = complaintService.updateComplaint(id, complaint);
        return (updatedComplaint != null) ? ResponseEntity.ok(updatedComplaint) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComplaint(@PathVariable Long id) {
        if (complaintService.getComplaintById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Complaint not found.");
        }
        complaintService.deleteComplaint(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Complaint deleted successfully.");
    }
    @GetMapping("/paged")
    public Page<Complaint> getComplaints(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return complaintService.getComplaints(page, size);
    }
    @PostMapping("/predict")
    public ResponseEntity<Object> getPrediction(@RequestBody Object[] features) {
        try {
            Object prediction = complaintService.getPrediction(features);
            return ResponseEntity.ok(prediction);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while getting prediction: " + e.getMessage());
        }
    }

    @PostMapping("/send-reminder")
    public ResponseEntity<String> sendReminderForPendingComplaints() {
        try {
            complaintService.sendReminderForPendingComplaints();
            return ResponseEntity.ok("Reminders sent for pending complaints.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending reminders: " + e.getMessage());
        }
    }
    @PostMapping("/recommend/{Id}")
    public ResponseEntity<String> recommendSolution(@PathVariable Long Id) {
        try {
            // Vérifier si la réclamation existe
            Complaint complaint = complaintRepository.findById(Id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réclamation introuvable"));

            // URL de l'API Flask
            String flaskUrl = "http://localhost:5000/recommend/" + Id;

            // Définir les en-têtes
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Créer une requête vide puisque Flask prend l'ID dans l'URL
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
                    // Retourner la solution recommandée
                    return ResponseEntity.ok("Réclamation trouvée : " + complaint.getDescription() + " - Solution recommandée : " + recommendedSolution);
                } else {
                    throw new RuntimeException("La solution recommandée est absente de la réponse.");
                }
            } else {
                throw new RuntimeException("Échec de la récupération de la solution. Code de statut : " + response.getStatusCode());
            }
        } catch (Exception e) {
            // Gestion des erreurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération de la solution : " + e.getMessage());
        }
    }
}