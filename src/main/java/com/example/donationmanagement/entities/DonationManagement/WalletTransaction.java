package com.example.donationmanagement.entities.DonationManagement;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    private int id_transaction;
    private int wallet_id;
    private enum TransactionType{
        Credit,
        Debit
    }

    private Double amount_transaction;
    private Date date_transaction;
    private String description;
    @ManyToOne
    private DigitalWallet digital_wallet;
}
