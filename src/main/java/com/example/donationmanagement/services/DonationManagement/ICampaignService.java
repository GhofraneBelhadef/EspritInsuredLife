package com.example.donationmanagement.services.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.Campaign;
import com.example.donationmanagement.entities.DonationManagement.Donation;

import java.io.IOException;
import java.util.List;

public interface ICampaignService {
    Campaign createCampaign(Campaign campaign);
    Campaign addDonationToCampaign(long campaign_id, Donation donation);
    List<Campaign> getAllCampaigns();
    Campaign getCampaignById(long campaign_id);
    double calculateProgress(long campaign_id);
    byte[] generateCampaignPdf(Long campaign_id) throws IOException;
}
