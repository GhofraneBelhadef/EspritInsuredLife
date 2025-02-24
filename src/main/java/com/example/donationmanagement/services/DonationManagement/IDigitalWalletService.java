package com.example.donationmanagement.services.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.DigitalWallet;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IDigitalWalletService {
    DigitalWallet add(DigitalWallet digitalWallet);
/*
    DigitalWallet update(DigitalWallet digitalWallet);
*/
    void remove(int id);

    DigitalWallet getById(int id);

    List<DigitalWallet> getAll();
}