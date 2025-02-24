package com.example.donationmanagement.controllers.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.Donation;
import com.example.donationmanagement.services.DonationManagement.IDonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donations")
public class DonationController {

    @Autowired
    private IDonationService donationService;
/*
    @PostMapping("/add")
    public Donation addDonation(@RequestBody Donation donation) {
        return donationService.add(donation);
    }*/

    @PutMapping("/update")
    public Donation updateDonation(@RequestBody Donation donation) {
        return donationService.update(donation);
    }

    @DeleteMapping("/remove/{id}")
    public void removeDonation(@PathVariable Long id) {
        donationService.remove(id);
    }

    @GetMapping("/get/{id}")
    public Donation getDonationById(@PathVariable Long id) {
        return donationService.getById(id);
    }

    @GetMapping("/all")
    public List<Donation> getAllDonations() {
        return donationService.getAll();
    }
}
