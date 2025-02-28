package com.example.donationmanagement.entities.RiskManagement;

import jakarta.persistence.Entity;
import lombok.Data;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Data
public class RiskFactorHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId; // Identifiant de l'historique
    @ManyToOne
    @JoinColumn(name = "risk_assessment_id", nullable = false)
    private RiskAssessment riskAssessment; // 🔹 Relation avec RiskAssessment
    private Long userId; // ID de l'utilisateur concerné
    private Long riskFactorId; // ID du facteur de risque modifié
    private int factorValue; // Nouvelle valeur du facteur
    private String description; // Description du changement
    private LocalDateTime changeDate; // Date et heure du changement
    private Long User; // L'utilisateur concerné par le facteur
}
