package com.example.donationmanagement.controllers.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.Donation;
import com.example.donationmanagement.services.DonationManagement.IDonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/donations")
public class DonationController {

    @Autowired
    private IDonationService donationService;

    @PostMapping("/add")
    public Donation addDonation(@RequestBody Donation donation) {
        return donationService.add(donation);
    }
    @GetMapping
    public Page<Donation> getAllDonations(Pageable pageable) {
        return donationService.getAllDonations(pageable);
    }
    @PutMapping("/update")
    public Donation updateDonation(@RequestBody Donation donation) {
        return donationService.update(donation);
    }

    @DeleteMapping("/remove/{donation_id}")
    public void removeDonation(@PathVariable Long donation_id) {
        donationService.remove(donation_id);
    }

    @GetMapping("/get/{donation_id}")
    public Donation getDonationById(@PathVariable Long donation_id) {
        return donationService.getById(donation_id);
    }

    @GetMapping("/all")
    public List<Donation> getAllDonations() {
        return donationService.getAll();
    }
    @GetMapping("/donor/{donorId}")
    public Page<Donation> getDonationsByDonorId(@PathVariable int donor_id, Pageable pageable) {
        return donationService.getDonationsByDonorId(donor_id, pageable);
    }

    @GetMapping("/amount-range")
    public Page<Donation> getDonationsByAmountRange(
            @RequestParam double minAmount,
            @RequestParam double maxAmount,
            Pageable pageable) {
        return donationService.getDonationsByAmountRange(minAmount, maxAmount, pageable);
    }
}
