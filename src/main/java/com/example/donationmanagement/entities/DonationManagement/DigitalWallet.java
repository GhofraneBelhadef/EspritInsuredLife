package com.example.donationmanagement.entities.DonationManagement;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@ToString
@Setter
@Getter
@NoArgsConstructor
@Entity
public class DigitalWallet {
    @Id
    private int id_digital_wallet;
    private Double balance;
    private int donor_id;
    private Date last_update;

    @OneToMany(mappedBy = "digital_wallet")
    private List<WalletTransaction> WalletTransactions;
}
