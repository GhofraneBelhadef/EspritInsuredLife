package com.example.donationmanagement.services.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.WalletTransaction;
import java.util.List;

public interface IWalletTransactionService {
    WalletTransaction add(WalletTransaction walletTransaction);
    WalletTransaction update(WalletTransaction walletTransaction);
    void remove(int id);
    WalletTransaction getById(int id);
    List<WalletTransaction> getAll();
}
