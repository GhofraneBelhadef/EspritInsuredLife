package com.example.donationmanagement.entities.ContractManagement;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

@ToString
@Setter
@Getter
@NoArgsConstructor
@Entity
public class ProvisionsTechniques {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "contract_id", nullable = false, unique = true)
    private Contract contract; // Lié à un contrat spécifique

    private float provision; // Montant de la provision technique

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt; // Date de création

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt; // Date de la dernière mise à jour

    @ManyToOne
    @JoinColumn(name = "contract_accounting_id")
    private ContractAccounting contractAccounting;

}
