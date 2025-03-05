package com.example.donationmanagement.entities.ClaimManagement;

import jakarta.persistence.*;

enum ComplaintStatus {
    OPEN, IN_PROGRESS, RESOLVED, REJECTED
}

@Entity
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Long contractId;
    private Double claimAmount;

    @Enumerated(EnumType.STRING)
    private ComplaintStatus status;

    @OneToOne(mappedBy = "complaint", cascade = CascadeType.ALL)
    private Notification notification;

    // Constructors
    public Complaint() {}

    public Complaint(String description, Long contractId, Double claimAmount, ComplaintStatus status) {
        this.description = description;
        this.contractId = contractId;
        this.claimAmount = claimAmount;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getContractId() { return contractId; }
    public void setContractId(Long contractId) { this.contractId = contractId; }

    public Double getClaimAmount() { return claimAmount; }
    public void setClaimAmount(Double claimAmount) { this.claimAmount = claimAmount; }

    public ComplaintStatus getStatus() { return status; }
    public void setStatus(ComplaintStatus status) { this.status = status; }

    public Notification getNotification() { return notification; }
    public void setNotification(Notification notification) { this.notification = notification; }
}
