package com.example.donationmanagement.services.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.DigitalWallet;
import com.example.donationmanagement.repositories.DonationManagement.DigitalWalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Slf4j
public class DigitalWalletService implements IDigitalWalletService {

    @Autowired
    private DigitalWalletRepository digitalWalletRepo;

    @Override
    public DigitalWallet add(DigitalWallet digitalWallet) {
        log.info("Adding digital wallet: {}", digitalWallet);
        return digitalWalletRepo.save(digitalWallet);
    }

    @Override
    public DigitalWallet update(DigitalWallet digitalWallet) {
        log.info("Updating digital wallet: {}", digitalWallet);
        return digitalWalletRepo.save(digitalWallet);
    }

    @Override
    public void remove(int id) {
        log.info("Removing digital wallet with ID: {}", id);
        digitalWalletRepo.deleteById(id);
    }

    @Override
    public DigitalWallet getById(int id) {
        log.info("Fetching digital wallet with ID: {}", id);
        return digitalWalletRepo.findById(id).orElse(null);
    }

    @Override
    public List<DigitalWallet> getAll() {
        log.info("Fetching all digital wallets");
        return digitalWalletRepo.findAll();
    }
}
