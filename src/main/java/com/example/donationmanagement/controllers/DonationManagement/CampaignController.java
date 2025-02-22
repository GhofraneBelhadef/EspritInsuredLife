package com.example.donationmanagement.controllers.DonationManagement;


import com.example.donationmanagement.entities.DonationManagement.Campaign;
import com.example.donationmanagement.entities.DonationManagement.Donation;
import com.example.donationmanagement.services.DonationManagement.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/{campaignId}/add-donation")
    public Campaign addDonationToCampaign(@PathVariable long campaignId, @RequestBody Donation donation) {
        return campaignService.addDonationToCampaign(campaignId, donation);
    }

    // Récupérer toutes les campagnes
    @GetMapping("/all")
    public List<Campaign> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }

    // Récupérer une campagne par son ID
    @GetMapping("/get/{id}")
    public Campaign getCampaignById(@PathVariable long id) {
        return campaignService.getCampaignById(id);
    }

    // Calculer le pourcentage de l'objectif atteint
    @GetMapping("/progress/{campaignId}")
    public double calculateProgress(@PathVariable long campaignId) {
        return campaignService.calculateProgress(campaignId);
    }
}