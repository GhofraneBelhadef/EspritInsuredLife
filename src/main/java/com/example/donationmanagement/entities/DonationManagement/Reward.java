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
public class Reward {
    @Id
    private Long id_reward;
    private int donation_id;
    private Double reward_amount;

    private String reward_type;
    private Date reward_date;

    private String description;

    @OneToOne
    private Donation donation;

}
