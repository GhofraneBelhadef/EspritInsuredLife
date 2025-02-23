package com.example.donationmanagement.entities.RiskManagement;


import jakarta.persistence.*;
import lombok.Data;


import java.util.List;
@Entity
@Data
public class RiskFactors {
    public enum FactorType {
        Health, Environnemental, Financial
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long RiskFactorsId;
    @Enumerated(EnumType.STRING)
    private FactorType FactorType;
    private Double FactorValue;
    private String Description;
    @ManyToMany(mappedBy = "RiskFactors")
    private List<RiskAssessment> RiskAssessments;
}