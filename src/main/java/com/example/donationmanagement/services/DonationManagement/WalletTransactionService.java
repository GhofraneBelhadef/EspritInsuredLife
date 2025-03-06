package com.example.donationmanagement.services.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.WalletTransaction;
import com.example.donationmanagement.repositories.DonationManagement.WalletTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public void remove(int transaction_id) {
        log.info("Removing wallet transaction with ID: {}", transaction_id);
        walletTransactionRepo.deleteById(transaction_id);
    }

    @Override
    public WalletTransaction getById(int transaction_id) {
        log.info("Fetching wallet transaction with ID: {}", transaction_id);
        return walletTransactionRepo.findById(transaction_id).orElse(null);
    }


    public Page<WalletTransaction> getAll(Pageable pageable) {
        return walletTransactionRepo.findAll(pageable);
    }

    public Page<WalletTransaction> getTransactionsByAmountRange(double minAmount, double maxAmount, Pageable pageable) {
        return walletTransactionRepo.findByAmountBetween(minAmount, maxAmount, pageable);
    }
}
