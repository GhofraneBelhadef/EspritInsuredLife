package com.example.donationmanagement.repositories.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.DigitalWallet;
import com.example.donationmanagement.entities.UserManagement.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DigitalWalletRepository extends JpaRepository<DigitalWallet, Integer> {
    User findByDonor(int donor);
}