package com.example.donationmanagement.controllers.DonationManagement;


import com.example.donationmanagement.entities.DonationManagement.Campaign;
import com.example.donationmanagement.entities.DonationManagement.Donation;
import com.example.donationmanagement.services.DonationManagement.CampaignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j // Add this annotation

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    // Créer une nouvelle campagne
    @PostMapping("/add")
    public Campaign createCampaign(@RequestBody Campaign campaign) {
        return campaignService.createCampaign(campaign);
    }
    // Ajouter une donation à une campagne
    @PostMapping("/{campaign_id}/add-donation")
    public Campaign addDonationToCampaign(@PathVariable long campaign_id, @RequestBody Donation donation) {
        log.info("Received request to add donation to campaign ID: {}", campaign_id);
        log.info("Donation data: {}", donation);

        Campaign updatedCampaign = campaignService.addDonationToCampaign(campaign_id, donation);

        log.info("Donation added successfully to campaign ID: {}", campaign_id);
        return updatedCampaign;
    }

    // Récupérer toutes les campagnes
    @GetMapping("/all")
    public List<Campaign> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }

    // Récupérer une campagne par son ID
    @GetMapping("/get/{campaign_id}")
    public Campaign getCampaignById(@PathVariable long campaign_id) {
        return campaignService.getCampaignById(campaign_id);
    }

    // Calculer le pourcentage de l'objectif atteint
    @GetMapping("/progress/{campaign_id}")
    public double calculateProgress(@PathVariable long campaign_id) {
        return campaignService.calculateProgress(campaign_id);
    }
    @PostMapping("/{campaign_id}/allocate")
    public ResponseEntity<String> allocateCampaignDonations(@PathVariable long campaign_id) {
        campaignService.allocateCampaignDonationsToContracts(campaign_id);
        return ResponseEntity.ok("Campaign donations allocated to contracts successfully.");
    }

    @PostMapping("/generate-content/{campaign_id}")
    public String generateContent(@PathVariable Long campaign_id) {
        return campaignService.generateSocialMediaContent(campaign_id);
    }
    @GetMapping("/{campaign_id}/donations")
    public ResponseEntity<List<Donation>> getDonationsByCampaignId(@PathVariable Long campaign_id) {
        List<Donation> donations = campaignService.getDonationsByCampaignId(campaign_id);
        return ResponseEntity.ok(donations);
    }
    // Générer un PDF pour une campagne
    @GetMapping("/{campaign_id}/pdf")
    public ResponseEntity<ByteArrayResource> downloadCampaignPdf(@PathVariable Long campaign_id) throws IOException, IOException {
        byte[] pdfBytes = campaignService.generateCampaignPdf(campaign_id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "campaign_report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(new ByteArrayResource(pdfBytes));
    }
}