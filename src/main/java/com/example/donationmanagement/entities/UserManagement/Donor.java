package com.example.donationmanagement.entities.UserManagement;
/*
import com.example.donationmanagement.entities.DonationManagement.Donation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Donor extends User {
    private double totalDonations; // Montant total des donations effectuées

    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL)
    private List<Donation> donations = new ArrayList<>();

    // Constructeur pour initialiser un donor
    public Donor(String nom, String email, String password) {
        super(nom, email, password); // Appelle le constructeur de la classe User
        this.totalDonations = 0.0; // Initialise le montant total des donations à 0
    }

    // Méthode pour ajouter une donation
    public void addDonation(Donation donation) {
        this.donations.add(donation);
        this.totalDonations += donation.getDonation_amount();
    }
}*/