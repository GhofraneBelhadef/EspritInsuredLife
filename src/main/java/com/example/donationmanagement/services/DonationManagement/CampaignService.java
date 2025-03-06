package com.example.donationmanagement.services.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.Campaign;
import com.example.donationmanagement.entities.DonationManagement.Donation;
import com.example.donationmanagement.repositories.DonationManagement.CampaignRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CampaignService implements ICampaignService  {

    @Autowired
    private CampaignRepository campaignRepository;

    // Créer une nouvelle campagne
    public Campaign createCampaign(Campaign campaign) {
        log.info("Creating campaign: {}", campaign);
        return campaignRepository.save(campaign);
    }

    // Ajouter une donation à une campagne
    public Campaign addDonationToCampaign(long campaignId, Donation donation) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new RuntimeException("Campagne non trouvée"));
        campaign.addDonation(donation);
        log.info("Adding donation to campaign: {}", campaign);
        return campaignRepository.save(campaign);
    }

    // Récupérer toutes les campagnes
    public List<Campaign> getAllCampaigns() {
        log.info("Fetching all campaigns");
        return campaignRepository.findAll();
    }

    // Récupérer une campagne par son ID
    public Campaign getCampaignById(long id) {
        log.info("Fetching campaign with ID: {}", id);
        return campaignRepository.findById(id).orElse(null);
    }

    // Calculer le pourcentage de l'objectif atteint
    public double calculateProgress(long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new RuntimeException("Campagne non trouvée"));
        return (campaign.getCollecte_amount() / campaign.getCampaign_objectif()) * 100;
    }
}