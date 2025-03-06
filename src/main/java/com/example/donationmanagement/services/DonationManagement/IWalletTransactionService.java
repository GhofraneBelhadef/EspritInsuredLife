package com.example.donationmanagement.services.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.WalletTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IWalletTransactionService {
    WalletTransaction add(WalletTransaction walletTransaction);
    WalletTransaction update(WalletTransaction walletTransaction);
    void remove(int transaction_id);
    WalletTransaction getById(int transaction_id);
    Page<WalletTransaction> getTransactionsByAmountRange(double minAmount, double maxAmount, Pageable pageable);
    Page<WalletTransaction> getAll(Pageable pageable);
}
