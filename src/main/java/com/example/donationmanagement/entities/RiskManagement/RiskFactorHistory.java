package com.example.donationmanagement.entities.RiskManagement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Data
public class RiskFactorHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId; // Identifiant de l'historique
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "risk_assessment_id", nullable = false)
    private RiskAssessment riskAssessment; // 🔹 Relation avec RiskAssessment
    private Long userId; // ID de l'utilisateur concerné
    private Long riskFactorId; // ID du facteur de risque modifié
    private int factorValue; // Nouvelle valeur du facteur
    private String description; // Description du changement
    private LocalDateTime changeDate; // Date et heure du changement
    @PrePersist
    protected void onCreate() {
        if (changeDate == null) {
            changeDate = LocalDateTime.now(); // 🔹 Définit la date automatiquement si elle n’est pas fournie
        }
    }
}
