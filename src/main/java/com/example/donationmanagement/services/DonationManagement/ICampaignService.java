package com.example.donationmanagement.services.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.Campaign;
import com.example.donationmanagement.entities.DonationManagement.Donation;

import java.util.List;

public interface ICampaignService {
    Campaign createCampaign(Campaign campaign);
    Campaign addDonationToCampaign(long campaignId, Donation donation);
    List<Campaign> getAllCampaigns();
    Campaign getCampaignById(long id);
    double calculateProgress(long campaignId);

}
