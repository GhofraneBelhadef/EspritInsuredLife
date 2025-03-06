package com.example.donationmanagement.entities.DonationManagement;

import com.example.donationmanagement.entities.ContractManagement.Contract;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long campaign_id;

    private String campaign_name;
    private String campaign_description;
    private double campaign_objectif;
    private double collecte_amount;
    private Date open_date;
    private Date closure_date;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Donation> donations = new ArrayList<>();

    // Nouveaux champs pour la gestion des médias sociaux
    private String socialMediaContent; // Contenu généré pour les réseaux sociaux
    private Date lastSocialMediaPostDate; // Date de la dernière publication
    private int socialMediaLikes; // Nombre de likes sur les publications
    private int socialMediaShares; // Nombre de partages sur les publications
    private int socialMediaComments; // Nombre de commentaires sur les publications

    public void addDonation(Donation donation) {
        this.donations.add(donation);
        this.collecte_amount += donation.getDonation_amount();
    }

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract; // Lien vers le contrat (si applicable)

    public enum Status {
        Active,
        Completed
    }
}