package com.example.donationmanagement.controllers.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.WalletTransaction;
import com.example.donationmanagement.services.DonationManagement.IWalletTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wallet-transactions")
public class WalletTransactionController {

    @Autowired
    private IWalletTransactionService walletTransactionService;

    @PostMapping("/add")
    public WalletTransaction addWalletTransaction(@RequestBody WalletTransaction walletTransaction) {
        return walletTransactionService.add(walletTransaction);
    }

    @PutMapping("/update")
    public WalletTransaction updateWalletTransaction(@RequestBody WalletTransaction walletTransaction) {
        return walletTransactionService.update(walletTransaction);
    }

    @DeleteMapping("/remove/{id}")
    public void removeWalletTransaction(@PathVariable int id) {
        walletTransactionService.remove(id);
    }

    @GetMapping("/get/{id}")
    public WalletTransaction getWalletTransactionById(@PathVariable int id) {
        return walletTransactionService.getById(id);
    }

    @GetMapping("/all")
    public List<WalletTransaction> getAllWalletTransactions() {
        return walletTransactionService.getAll();
    }
}
