package com.example.donationmanagement.services.DonationManagement;
import com.example.donationmanagement.entities.ContractManagement.Contract;
import com.example.donationmanagement.entities.DonationManagement.Campaign;
import com.example.donationmanagement.entities.DonationManagement.Donation;
import com.example.donationmanagement.repositories.ContractManagement.ContractRepository;
import com.example.donationmanagement.repositories.DonationManagement.CampaignRepository;
import com.example.donationmanagement.repositories.DonationManagement.DonationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CampaignService implements ICampaignService  {

    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private PdfService pdfService; // Injecter le service PDF

    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private SocialMediaService socialMediaService;

    // Méthode pour générer du contenu pour une campagne
    public String generateSocialMediaContent(Long campaign_id) {
        Campaign campaign = campaignRepository.findById(campaign_id)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        // Appeler l'API Flask pour générer du contenu
        String generatedContent = socialMediaService.generateContent(campaign_id);

        // Mettre à jour la campagne avec le contenu généré
        campaign.setSocialMediaContent(generatedContent);
        campaignRepository.save(campaign);

        return generatedContent;
    }

    public byte[] generateCampaignPdf(Long campaign_id) throws IOException {
        Campaign campaign = campaignRepository.findById(campaign_id)
                .orElseThrow(() -> new RuntimeException("Campaign not found with ID: " + campaign_id));

        List<Donation> donations = donationRepository.findByCampaignId(campaign_id);
        if (donations.isEmpty()) {
            throw new RuntimeException("No donations found for campaign with ID: " + campaign_id);
        }

        return pdfService.generateCampaignPdf(campaign, donations);
    }
    public List<Donation> getDonationsByCampaignId(Long campaign_id) {
        return donationRepository.findByCampaignId(campaign_id);
    }
    // Créer une nouvelle campagne
    public Campaign createCampaign(Campaign campaign) {
        log.info("Creating campaign: {}", campaign);
        return campaignRepository.save(campaign);
    }

    // Ajouter une donation à une campagne
    public Campaign addDonationToCampaign(long campaign_id, Donation donation) {
        log.info("Adding donation to campaign ID: {}", campaign_id);

        Campaign campaign = campaignRepository.findById(campaign_id)
                .orElseThrow(() -> {
                    log.error("Campaign not found with ID: {}", campaign_id);
                    return new RuntimeException("Campaign not found with ID: " + campaign_id);
                });

        log.info("Found campaign: {}", campaign.getCampaign_name());

        campaign.addDonation(donation);
        log.info("Donation added to campaign: {}", donation);

        return campaignRepository.save(campaign);
    }

    // Récupérer toutes les campagnes
    public List<Campaign> getAllCampaigns() {
        log.info("Fetching all campaigns");
        return campaignRepository.findAll();
    }

    // Récupérer une campagne par son ID
    public Campaign getCampaignById(long campaign_id) {
        log.info("Fetching campaign with ID: {}", campaign_id);
        return campaignRepository.findById(campaign_id).orElse(null);
    }

    // Calculer le pourcentage de l'objectif atteint
    public double calculateProgress(long campaign_id) {
        Campaign campaign = campaignRepository.findById(campaign_id)
                .orElseThrow(() -> new RuntimeException("Campagne non trouvée"));
        return (campaign.getCollecte_amount() / campaign.getCampaign_objectif()) * 100;
    }
    public void allocateCampaignDonationsToContracts(long campaign_id) {
        // Fetch the campaign
        Campaign campaign = campaignRepository.findById(campaign_id)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        // Fetch all contracts
        List<Contract> contracts = contractRepository.findAll();

        // Calculate the total amount collected for the campaign
        double totalCollected = campaign.getCollecte_amount();

        // Divide the total collected amount equally among contracts
        double amountPerContract = totalCollected / contracts.size();

        // Create a new donation for each contract
        for (Contract contract : contracts) {
            Donation donation = new Donation();
            donation.setContract(contract);
            donation.setDonation_amount(amountPerContract);
            donation.setDonation_date(new Date());
            donation.setStatusDonation(Donation.StatusDonation.Completed); // Mark as allocated

            // Save the donation
            donationRepository.save(donation);

            // Update the contract's total donations (if needed)
            contract.setTotalDonations(contract.getTotalDonations() + amountPerContract);
            contractRepository.save(contract);
        }

        // Mark the campaign as completed (optional)
        campaign.setStatus(Campaign.Status.Completed);
        campaignRepository.save(campaign);
    }
}