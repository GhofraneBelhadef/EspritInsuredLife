package com.example.donationmanagement.entities.DonationManagement;

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

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Donation> donations = new ArrayList<>();

    public void addDonation(Donation donation) {
        this.donations.add(donation);
        this.collecte_amount += donation.getDonation_amount();
    }
}