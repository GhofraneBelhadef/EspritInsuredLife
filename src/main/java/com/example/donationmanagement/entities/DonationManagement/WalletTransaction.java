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
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transaction_id;

    private Double amount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date transaction_date;

    @ManyToOne
    @JoinColumn(name = "digital_wallet_id")
    private DigitalWallet digital_wallet; // Link to the digital wallet
}