package com.example.donationmanagement.controllers.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.DigitalWallet;
import com.example.donationmanagement.services.DonationManagement.IDigitalWalletService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @DeleteMapping("/remove/{id}")
    public void removeDigitalWallet(@PathVariable int id) {
        digitalWalletService.remove(id);
    }

    @GetMapping("/get/{id}")
    public DigitalWallet getDigitalWalletById(@PathVariable int id) {
        return digitalWalletService.getById(id);
    }

    @GetMapping("/all")
    public List<DigitalWallet> getAllDigitalWallets() {
        return digitalWalletService.getAll();
    }
}