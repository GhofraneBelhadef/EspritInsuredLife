package com.example.donationmanagement.entities.RiskManagement;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    private int FactorValue;
    private String Description;
    @ManyToMany(mappedBy = "RiskFactors")
    @JsonIgnore
    private List<RiskAssessment> RiskAssessments;
}

