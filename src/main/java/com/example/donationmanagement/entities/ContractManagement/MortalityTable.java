package com.example.donationmanagement.entities.ContractManagement;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@NoArgsConstructor
@Entity
public class MortalityTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int age;
    private float probabilityOfDeath; // Probabilité de décès à cet âge
    public MortalityTable(int age, float probabilityOfDeath) {
        this.age = age;
        this.probabilityOfDeath = probabilityOfDeath;
    }


}
