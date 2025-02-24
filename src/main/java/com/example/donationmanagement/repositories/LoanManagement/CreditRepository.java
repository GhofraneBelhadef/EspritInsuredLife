package com.example.donationmanagement.repositories.LoanManagement;

import com.example.donationmanagement.entities.LoanManagement.Credit; // Updated import
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {
}
