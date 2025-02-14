package com.example.donationmanagement.entities.DonationManagement;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
public class Donation {
    @Id
    private Long id_donation;

    private int donor_id;
    private int contract_id;

    private double donation_amount;

    private Date donation_date;
    private enum StatusDonation{
        Pending,
        Completed,
        Cancelled
    }
    @OneToOne(mappedBy = "donation")
    private Reward reward;
}
