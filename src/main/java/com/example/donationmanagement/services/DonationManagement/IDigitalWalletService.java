package com.example.donationmanagement.services.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.DigitalWallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IDigitalWalletService {
    DigitalWallet add(DigitalWallet digitalWallet);

    DigitalWallet update(DigitalWallet digitalWallet);

    void remove(int digital_wallet_id);

    DigitalWallet getById(int digital_wallet_id);


    Page<DigitalWallet> getAll(Pageable pageable);

    Page<DigitalWallet> getWalletsByBalanceRange(double minBalance, double maxBalance, Pageable pageable);
}