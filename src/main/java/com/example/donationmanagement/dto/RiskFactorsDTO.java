package com.example.donationmanagement.dto;
import com.example.donationmanagement.entities.RiskManagement.RiskFactors;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
    public class RiskFactorsDTO {
    @Min(value = 1, message = "La valeur du facteur ne peut pas être négative")
    @Max(value = 100, message = "La valeur du facteur ne doit pas dépasser 100")
        private int factorValue;
        private RiskFactors.FactorType factorType; // ENUM
    @Size(max = 255, message = "La description ne doit pas dépasser 255 caractères")
        private String description;
    }


