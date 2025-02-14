package com.example.donationmanagement.services.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.WalletTransaction;
import com.example.donationmanagement.repositories.DonationManagement.WalletTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WalletTransactionService implements IWalletTransactionService {

    @Autowired
    private WalletTransactionRepository walletTransactionRepo;

    @Override
    public WalletTransaction add(WalletTransaction walletTransaction) {
        log.info("Adding wallet transaction: {}", walletTransaction);
        return walletTransactionRepo.save(walletTransaction);
    }

    @Override
    public WalletTransaction update(WalletTransaction walletTransaction) {
        log.info("Updating wallet transaction: {}", walletTransaction);
        return walletTransactionRepo.save(walletTransaction);
    }

    @Override
    public void remove(int id) {
        log.info("Removing wallet transaction with ID: {}", id);
        walletTransactionRepo.deleteById(id);
    }

    @Override
    public WalletTransaction getById(int id) {
        log.info("Fetching wallet transaction with ID: {}", id);
        return walletTransactionRepo.findById(id).orElse(null);
    }

    @Override
    public List<WalletTransaction> getAll() {
        log.info("Fetching all wallet transactions");
        return walletTransactionRepo.findAll();
    }
}
