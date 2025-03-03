package com.example.donationmanagement.entities.RiskManagement;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;



import java.math.BigDecimal;

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
    private Double RiskScore = 0.0;
    private BigDecimal Price;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "RiskA_RiskF",
            joinColumns = @JoinColumn(name = "idAssesment"),
            inverseJoinColumns = @JoinColumn(name = "idFactor")
    )
    private List<RiskFactors> RiskFactors;

}

