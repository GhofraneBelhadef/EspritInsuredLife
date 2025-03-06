
package com.example.donationmanagement.entities.ContractManagement;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@Table(name="contract_accounting")
@ToString
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Contract_Accounting {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long contract_accounting_id;
    private int matricule_fiscale;
    private float total_capital;
    private float indemnites_versees;
    private Date created_at;
    private Date updated_at;

    // Correction de l'espace dans "mappedBy"
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contract_accounting")
    private Set<Contract> contracts;

    // Getters and setters
}
