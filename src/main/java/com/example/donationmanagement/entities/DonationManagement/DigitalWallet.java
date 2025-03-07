package com.example.donationmanagement.entities.DonationManagement;

import com.example.donationmanagement.entities.UserManagement.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long digital_wallet_id;

    private Double balance;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private User donor; // Link to the donor (User entity)


    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date last_update;

    @OneToMany(mappedBy = "digital_wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WalletTransaction> walletTransactions; // Transactions made from this wallet
}