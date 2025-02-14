package com.example.donationmanagement.services.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.Donation;
import com.example.donationmanagement.repositories.DonationManagement.DonationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DonationService implements IDonationService {

    @Autowired
    private DonationRepository donationRepo;

    @Override
    public Donation add(Donation donation) {
        log.info("Adding donation: {}", donation);
        return donationRepo.save(donation);
    }

    @Override
    public Donation update(Donation donation) {
        log.info("Updating donation: {}", donation);
        return donationRepo.save(donation);
    }

    @Override
    public void remove(Long id) {
        log.info("Removing donation with ID: {}", id);
        donationRepo.deleteById(id);
    }

    @Override
    public Donation getById(Long id) {
        log.info("Fetching donation with ID: {}", id);
        return donationRepo.findById(id).orElse(null);
    }

    @Override
    public List<Donation> getAll() {
        log.info("Fetching all donations");
        return donationRepo.findAll();
    }
}
