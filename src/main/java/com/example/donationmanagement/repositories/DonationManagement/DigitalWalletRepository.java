package com.example.donationmanagement.repositories.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.DigitalWallet;
import com.example.donationmanagement.entities.UserManagement.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DigitalWalletRepository extends JpaRepository<DigitalWallet, Integer> {
    DigitalWallet findByDonorId(Long donor_id); // Recherche par identifiant du donateur
    @Query("SELECT w FROM DigitalWallet w WHERE w.balance >= :minBalance AND w.balance <= :maxBalance")
    Page<DigitalWallet> findByBalanceBetween(@Param("minBalance") double minBalance, @Param("maxBalance") double maxBalance, Pageable pageable);
}