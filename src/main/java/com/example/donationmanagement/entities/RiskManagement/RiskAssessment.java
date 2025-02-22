package com.example.donationmanagement.entities.RiskManagement;


import jakarta.persistence.*;
import lombok.Data;


import java.util.List;
@Entity
@Data

public class RiskAssessment {
    public enum RiskType {
        High, Medium, Low
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AssessmentId;
    private Long UserId;
    @Enumerated(EnumType.STRING)
    private RiskType RiskType;
    private double RiskScore;
    private double Price;
    @ManyToMany
    @JoinTable(
            name = "RiskA_RiskF",
            joinColumns = @JoinColumn(name = "idAssesment"),
            inverseJoinColumns = @JoinColumn(name = "idFactor")
    )
    private List<RiskFactors> RiskFactors;
}