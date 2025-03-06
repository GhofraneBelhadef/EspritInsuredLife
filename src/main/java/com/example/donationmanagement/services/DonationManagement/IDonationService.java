package com.example.donationmanagement.services.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IDonationService {
    Donation add(Donation donation);

    Donation update(Donation donation);
    void remove(Long donation_id);
    Donation getById(Long donation_id);
    List<Donation> getAll();

    Page<Donation> getAllDonations(Pageable pageable);

    Page<Donation> getDonationsByDonorId(int donorId, Pageable pageable);

    Page<Donation> getDonationsByAmountRange(double minAmount, double maxAmount, Pageable pageable);
}
