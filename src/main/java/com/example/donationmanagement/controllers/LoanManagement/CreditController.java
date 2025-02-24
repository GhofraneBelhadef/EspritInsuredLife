package com.example.donationmanagement.controllers.LoanManagement;

import com.example.donationmanagement.entities.LoanManagement.Credit;
import com.example.donationmanagement.services.LoanManagement.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/credit")
public class CreditController {

    @Autowired
    private CreditService creditService;

    @GetMapping
    public List<Credit> getAllCredits() {
        return creditService.getAllCredits();
    }

    @GetMapping("/{id}")
    public Optional<Credit> getCreditById(@PathVariable Long id) {
        return creditService.getCreditById(id);
    }

    @PostMapping
    public Credit createCredit(@RequestBody Credit credit) {
        return creditService.saveCredit(credit);
    }

    @PutMapping("/{id}")
    public Credit updateCredit(@PathVariable Long id, @RequestBody Credit credit) {
        credit.setIdCredit(id);
        return creditService.saveCredit(credit);
    }

    @DeleteMapping("/{id}")
    public void deleteCredit(@PathVariable Long id) {
        creditService.deleteCredit(id);
    }
}
