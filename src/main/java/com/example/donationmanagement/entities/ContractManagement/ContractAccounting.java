package com.example.donationmanagement.entities.ContractManagement;

import jakarta.persistence.*;
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
        private int matriculeFiscale;
        private float total_capital;
        private float indemnites_versees;
        private Date created_at;
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


