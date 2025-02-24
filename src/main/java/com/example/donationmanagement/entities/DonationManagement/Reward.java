package com.example.donationmanagement.entities.DonationManagement;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reward_id;

    private Double reward_amount;
    private String reward_type;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reward_date;

    private String description;

    @OneToOne
    @JoinColumn(name = "donation_id")
    private Donation donation; // Link to the donation
}