package com.example.donationmanagement.services.DonationManagement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.donationmanagement.entities.ContractManagement.Contract;
import com.example.donationmanagement.entities.DonationManagement.Campaign;
import com.example.donationmanagement.entities.DonationManagement.DigitalWallet;
import com.example.donationmanagement.entities.DonationManagement.Donation;
import com.example.donationmanagement.entities.DonationManagement.Reward;
import com.example.donationmanagement.entities.UserManagement.User;
import com.example.donationmanagement.repositories.ContractManagement.ContractRepository;
import com.example.donationmanagement.repositories.DonationManagement.CampaignRepository;
import com.example.donationmanagement.repositories.DonationManagement.DigitalWalletRepository;
import com.example.donationmanagement.repositories.DonationManagement.DonationRepository;
import com.example.donationmanagement.repositories.DonationManagement.RewardRepository;
import com.example.donationmanagement.repositories.UserManagement.UserRepository;
import com.example.donationmanagement.services.UserManagement.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
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
    @Autowired
    private UserService userService;


    @Override
    public Donation add(Donation donation) {
        log.info("Adding donation: {}", donation);

        // Set donation status to Pending initially
        donation.setStatusDonation(Donation.StatusDonation.Pending);

        // Save the donation
        Donation savedDonation = donationRepo.save(donation);

        // Process the donation (e.g., check if it's for a campaign or contract)
        processDonation(savedDonation);

        return savedDonation;
    }

    @Override
    public Donation update(Donation donation) {
        log.info("Updating donation: {}", donation);
        return donationRepo.save(donation);
    }

    @Override
    public void remove(Long donation_id) {
        log.info("Removing donation with ID: {}", donation_id);
        donationRepo.deleteById(donation_id);
    }

    @Override
    public Donation getById(Long donation_id) {
        log.info("Fetching donation with ID: {}", donation_id);
        return donationRepo.findById(donation_id).orElse(null);
    }

    @Override
    public List<Donation> getAll() {
        log.info("Fetching all donations");
        return donationRepo.findAll();
    }

    private void processDonation(Donation donation) {
        // Check if the donation is for a campaign or contract
        if (donation.getCampaign() != null) {
            processCampaignDonation(donation);
        } else if (donation.getContract() != null) {
            processContractDonation(donation);
        } else {
            log.error("Donation must be associated with either a campaign or a contract");
            throw new IllegalArgumentException("Donation must be associated with either a campaign or a contract");
        }
    }


    // Process donation for a campaign
    private void processCampaignDonation(Donation donation) {
        Campaign campaign = donation.getCampaign();

        // Add the donation to the campaign
        campaign.addDonation(donation);
        campaignRepo.save(campaign);

        // Mark the donation as completed
        donation.setStatusDonation(Donation.StatusDonation.Completed);
        /* if (donation.getStatusDonation())*/
        donationRepo.save(donation);

        // Create a reward for the donor
        createReward(donation);
    }

    // Process donation for a contract
    private void processContractDonation(Donation donation) {
        // Récupérer le contrat associé à la donation
        Long contractId = donationRepo.findContractIdByDonationId(donation.getDonation_id());
        Contract contract = contractRepo.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Contract not found with id: " + contractId));

        // Initialiser la collection des donations si elle est null
        if (contract.getDonations() == null) {
            contract.setDonations(new HashSet<>());
        }

        // Ajouter la donation au contrat
        contract.getDonations().add(donation);
        contract.setTotalDonations(contract.getTotalDonations() + donation.getDonation_amount()); // Mettre à jour le total des donations

        // Sauvegarder le contrat mis à jour
        contractRepo.save(contract);

        // Marquer la donation comme complétée
        donation.setStatusDonation(Donation.StatusDonation.Completed);
        donationRepo.save(donation);

        // Créer une récompense pour le donateur
        createReward(donation);
    }

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
    public Page<Donation> getAllDonations(Pageable pageable) {
        return donationRepo.findAll(pageable);
    }

    public Page<Donation> getDonationsByDonorId(int donor_id, Pageable pageable) {
        return donationRepo.findByDonorId(donor_id, pageable);
    }

    public Page<Donation> getDonationsByAmountRange(double minAmount, double maxAmount, Pageable pageable) {
        return donationRepo.findByDonationAmountBetween(minAmount, maxAmount, pageable);
    }

    // Add the reward to the donor's digital wallet
    private void addRewardToDigitalWallet(int donor_id, Double rewardAmount) {
        // Rechercher le portefeuille numérique par l'identifiant du donateur
        DigitalWallet wallet = digitalWalletRepo.findByDonorId((long) donor_id);

        if (wallet == null) {
            // Créer un nouveau portefeuille numérique s'il n'existe pas
            wallet = new DigitalWallet();

            // Récupérer l'utilisateur à partir de son identifiant
            User donor = userRepo.findById((long) donor_id)
                    .orElseThrow(() -> new RuntimeException("Donor not found"));

            // Définir le donateur et le solde initial
            wallet.setDonor(donor); // Assurez-vous que `setDonor` accepte un objet `User`
            wallet.setBalance(0.0);
        }

        // Mettre à jour le solde du portefeuille
        wallet.setBalance(wallet.getBalance() + rewardAmount);
        wallet.setLast_update(new Date());

        // Sauvegarder le portefeuille
        digitalWalletRepo.save(wallet);
    }
}