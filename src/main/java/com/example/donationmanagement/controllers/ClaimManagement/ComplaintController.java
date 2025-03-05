package com.example.donationmanagement.controllers.ClaimManagement;

import com.example.donationmanagement.entities.ClaimManagement.Complaint;
import com.example.donationmanagement.services.ClaimManagement.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

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
}