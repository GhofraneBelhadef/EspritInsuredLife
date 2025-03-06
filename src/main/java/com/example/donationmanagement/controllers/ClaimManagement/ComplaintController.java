package com.example.donationmanagement.controllers.ClaimManagement;
import com.example.donationmanagement.entities.UserManagement.User;
import com.example.donationmanagement.services.UserManagement.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.donationmanagement.entities.ClaimManagement.Complaint;
import com.example.donationmanagement.services.ClaimManagement.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;
    @Autowired
    private UserService userService;
    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);  // Extraire le token après "Bearer "
        }
        return null;
    }
    @PostMapping
    public ResponseEntity<Complaint> createComplaint(@RequestBody Complaint complaint, HttpServletRequest request) {
        // Extraire le token de la requête
        String token = extractToken(request);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 Unauthorized si le token est absent
        }

        // Extraire l'utilisateur authentifié à partir du token
        Optional<User> authenticatedUser = userService.getAuthenticatedUser(request);

        // Utilisation de JwtService
        if (authenticatedUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 Unauthorized si l'utilisateur est non authentifié
        }

        // Passer l'utilisateur authentifié au service
        complaint.setUser(authenticatedUser.get());
        Complaint savedComplaint = complaintService.createComplaint(complaint, request);
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
    @GetMapping("/complaints")
    public Page<Complaint> getComplaints(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return complaintService.getComplaints(page, size);
    }
}