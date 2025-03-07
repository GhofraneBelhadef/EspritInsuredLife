package com.example.donationmanagement.entities.ContractManagement;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.Set;

@ToString(exclude = {"contracts"})
@Setter
@Getter
@NoArgsConstructor
@Entity
public class ContractAccounting {

    public static final int MATRICULE_FISCALE_VIE = 1001;
    public static final int MATRICULE_FISCALE_NON_VIE = 2002;

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long contract_accounting_id;

    @Column(name = "matricule_fiscale", nullable = false, unique = true)
    @NotNull(message = "Le matricule fiscal ne peut pas être nul.")
    @Min(value = 1000, message = "Le matricule fiscal doit être supérieur ou égal à 1000.")
    @Max(value = 9999, message = "Le matricule fiscal doit être inférieur ou égal à 9999.")
    private int matriculeFiscale;
    private float total_capital;
    private float indemnites_versees;
    @NotNull(message = "La date de création ne peut pas être nulle.")
    @Past(message = "La date de création doit être dans le passé.")
    private Date created_at;

    @PastOrPresent(message = "La date de mise à jour doit être dans le passé ou présente.")
    private Date updated_at;
    private float totalProvisions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contractAccounting")
    private Set<Contract> contracts;
    @OneToMany(mappedBy = "contractAccounting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProvisionsTechniques> provisionsTechniques;
    public void updateTotalCapital() {
        if (contracts != null) {
            this.total_capital = (float) contracts.stream()
                    .filter(c -> c.getStatus() == Contract.Status.Active) // Ne prendre que les contrats actifs
                    .mapToDouble(Contract::getMonthly_price)
                    .sum();
        } else {
            this.total_capital = 0;
        }

    }
    public void updateCalculatedFields() {
        if (contracts != null) {
            // 🟢 Calcul du total_capital : Somme des prix mensuels des contrats actifs
            this.total_capital = (float) contracts.stream()
                    .filter(c -> c.getStatus() == Contract.Status.Active)
                    .mapToDouble(Contract::getMonthly_price)
                    .sum();
        } else {
            this.total_capital = 0;
        }}



}