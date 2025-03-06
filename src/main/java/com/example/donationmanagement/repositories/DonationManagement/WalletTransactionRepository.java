package com.example.donationmanagement.repositories.DonationManagement;
import com.example.donationmanagement.entities.DonationManagement.WalletTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Integer> {
    @Query("SELECT t FROM WalletTransaction t WHERE t.amount >= :minAmount AND t.amount <= :maxAmount")
    Page<WalletTransaction> findByAmountBetween(@Param("minAmount") double minAmount, @Param("maxAmount") double maxAmount, Pageable pageable);
}
