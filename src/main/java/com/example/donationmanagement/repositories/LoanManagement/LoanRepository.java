package com.example.donationmanagement.repositories.LoanManagement;

import com.example.donationmanagement.entities.LoanManagement.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
}
