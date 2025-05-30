package com.example.donationmanagement.repositories.DonationManagement;


import com.example.donationmanagement.entities.DonationManagement.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}