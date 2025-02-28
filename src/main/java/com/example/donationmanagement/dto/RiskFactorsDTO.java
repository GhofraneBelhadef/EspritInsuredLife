package com.example.donationmanagement.dto;
import com.example.donationmanagement.entities.RiskManagement.RiskFactors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
    public class RiskFactorsDTO {
        private int factorValue;
        private RiskFactors.FactorType factorType; // ENUM
        private String description;
    }


