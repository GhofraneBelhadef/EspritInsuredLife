package com.example.donationmanagement.controllers.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.DigitalWallet;
import com.example.donationmanagement.services.DonationManagement.IDigitalWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/digital-wallets")
public class DigitalWalletController {

    @Autowired
    private IDigitalWalletService digitalWalletService;

    @PostMapping("/add")
    public DigitalWallet addDigitalWallet(@RequestBody DigitalWallet digitalWallet) {
        return digitalWalletService.add(digitalWallet);
    }

    @PutMapping("/update")
    public DigitalWallet updateDigitalWallet(@RequestBody DigitalWallet digitalWallet) {
        return digitalWalletService.update(digitalWallet);
    }

    @DeleteMapping("/remove/{digital_wallet_id}")
    public void removeDigitalWallet(@PathVariable int digital_wallet_id) {
        digitalWalletService.remove(digital_wallet_id);
    }
    @GetMapping("/all")
    public Page<DigitalWallet> getAllDigitalWallets(Pageable pageable) {
        return digitalWalletService.getAll(pageable);
    }

    @GetMapping("/balance-range")
    public Page<DigitalWallet> getWalletsByBalanceRange(
            @RequestParam double minBalance,
            @RequestParam double maxBalance,
            Pageable pageable) {
        return digitalWalletService.getWalletsByBalanceRange(minBalance, maxBalance, pageable);
    }

    @GetMapping("/get/{digital_wallet_id}")
    public DigitalWallet getDigitalWalletById(@PathVariable int digital_wallet_id) {
        return digitalWalletService.getById(digital_wallet_id);
    }


}