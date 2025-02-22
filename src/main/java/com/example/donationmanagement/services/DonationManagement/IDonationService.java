package com.example.donationmanagement.services.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.Donation;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IDonationService {
   /* Donation add(Donation donation);*/
    Donation update(Donation donation);
    void remove(Long id);
    Donation getById(Long id);
    List<Donation> getAll();
}
