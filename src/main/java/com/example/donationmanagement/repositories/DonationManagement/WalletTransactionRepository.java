package com.example.donationmanagement.repositories.DonationManagement;
import com.example.donationmanagement.entities.DonationManagement.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Integer> {
}
