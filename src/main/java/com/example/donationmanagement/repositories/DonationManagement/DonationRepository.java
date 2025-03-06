package com.example.donationmanagement.repositories.DonationManagement;
import com.example.donationmanagement.entities.DonationManagement.Donation;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface DonationRepository  extends JpaRepository<Donation, Long>{

    // Trouver une donation par son ID
    @Query("SELECT d FROM Donation d WHERE d.donation_id = :donationId")
    Donation findDonationById(@Param("donationId") Long donationId);

    // Mettre à jour le statut d'une donation
    @Modifying
    @Transactional
    @Query("UPDATE Donation d SET d.StatusDonation = :status WHERE d.donation_id = :donationId")
    void updateDonationStatus(@Param("donationId") Long donationId, @Param("status") Donation.StatusDonation status);
    // Récupérer l'ID du contrat associé à une donation

    @Query("SELECT d.contract.contract_id FROM Donation d WHERE d.donation_id = :donationId")
    Long findContractIdByDonationId(@Param("donationId") Long donationId);

    @Query("SELECT d FROM Donation d WHERE d.campaign.campaign_id = :campaignId")
    List<Donation> findByCampaignId(@Param("campaign_id") Long campaign_id);
    @Query("SELECT d FROM Donation d WHERE d.donor_id = :donorId")
    Page<Donation> findByDonorId(@Param("donorId") int donorId, Pageable pageable);

    @Query("SELECT d FROM Donation d WHERE d.donation_amount >= :minAmount AND d.donation_amount <= :maxAmount")
    Page<Donation> findByDonationAmountBetween(@Param("minAmount") double minAmount, @Param("maxAmount") double maxAmount, Pageable pageable);
}
