package com.example.donationmanagement.controllers.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.WalletTransaction;
import com.example.donationmanagement.services.DonationManagement.IWalletTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @DeleteMapping("/remove/{transaction_id}")
    public void removeWalletTransaction(@PathVariable int transaction_id) {
        walletTransactionService.remove(transaction_id);
    }

    @GetMapping("/get/{transaction_id}")
    public WalletTransaction getWalletTransactionById(@PathVariable int transaction_id) {
        return walletTransactionService.getById(transaction_id);
    }


    @GetMapping("/all")
    public Page<WalletTransaction> getAll(Pageable pageable) {
        return walletTransactionService.getAll(pageable);
    }

    @GetMapping("/amount-range")
    public Page<WalletTransaction> getTransactionsByAmountRange(
            @RequestParam double minAmount,
            @RequestParam double maxAmount,
            Pageable pageable) {
        return walletTransactionService.getTransactionsByAmountRange(minAmount, maxAmount, pageable);
    }
}
