package com.example.donationmanagement.services.DonationManagement;

import com.example.donationmanagement.entities.ContractManagement.Contract;
import com.example.donationmanagement.entities.DonationManagement.*;
import com.example.donationmanagement.repositories.ContractManagement.ContractRepository;
import com.example.donationmanagement.repositories.DonationManagement.*;
import com.example.donationmanagement.repositories.UserManagement.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DonationService implements IDonationService {

    @Autowired
    private DonationRepository donationRepo;

    @Autowired
    private CampaignRepository campaignRepo;

    @Autowired
    private ContractRepository contractRepo;

    @Autowired
    private RewardRepository rewardRepo;

    @Autowired
    private DigitalWalletRepository digitalWalletRepo;

    @Autowired
    private UserRepository userRepo;
/*
    @Override
    public Donation add(Donation donation) {
        log.info("Adding donation: {}", donation);

        // Set donation status to Pending initially
        donation.settatus(Donation.status.Pending);

        // Save the donation
        Donation savedDonation = donationRepo.save(donation);

        // Process the donation (e.g., check if it's for a campaign or contract)
        processDonation(savedDonation);

        return savedDonation;
    }*/

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
/*
    // Helper method to process the donation
    private void processDonation(Donation donation) {
        // Check if the donation is for a campaign or contract
        if (donation.getCampaign() != null) {
            processCampaignDonation(donation);
        } else if (donation.getContract_id() != null) {
            processContractDonation(donation);
        } else {
            log.error("Donation must be associated with either a campaign or a contract");
            throw new IllegalArgumentException("Donation must be associated with either a campaign or a contract");
        }
    }
/*
    // Process donation for a campaign
    private void processCampaignDonation(Donation donation) {
        Campaign campaign = donation.getCampaign();

        // Add the donation to the campaign
        campaign.addDonation(donation);
        campaignRepo.save(campaign);

        // Mark the donation as completed
        donation.setStatus(Donation.status.Completed);
        donationRepo.save(donation);

        // Create a reward for the donor
        createReward(donation);
    }

    // Process donation for a contract
    private void processContractDonation(Donation donation) {
        int contract = donation.getContract_id();

        // Add the donation to the contract
        contract.getDonations().add(donation);
        contractRepo.save(contract);

        // Mark the donation as completed
        donation.setStatus(Donation.status.Completed);
        donationRepo.save(donation);

        // Create a reward for the donor
        createReward(donation);
    }
*//*
    // Create a reward for the donor
    private void createReward(Donation donation) {
        Reward reward = new Reward();
        reward.setDonation(donation);
        reward.setReward_amount(donation.getDonation_amount() * 0.1); // Example: 10% reward
        reward.setReward_type("Donation Reward");
        reward.setReward_date(new Date());
        reward.setDescription("Reward for donation to " + (donation.getCampaign() != null ? "Campaign" : "Contract"));
        rewardRepo.save(reward);

        // Add the reward to the donor's digital wallet
        addRewardToDigitalWallet(donation.getDonor_id(), reward.getReward_amount());
    }
/*
    // Add the reward to the donor's digital wallet
    private void addRewardToDigitalWallet(int donor, Double rewardAmount) {
        DigitalWallet wallet = digitalWalletRepo.findByDonor(donor);

        if (wallet == null) {
            // Create a new digital wallet if it doesn't exist
            wallet = new DigitalWallet();
            wallet.setDonor(user);
            wallet.setBalance(0.0);
        }

        // Update the wallet balance
        wallet.setBalance(wallet.getBalance() + rewardAmount);
        wallet.setLast_update(new Date());
        digitalWalletRepo.save(wallet);
    }*/
}