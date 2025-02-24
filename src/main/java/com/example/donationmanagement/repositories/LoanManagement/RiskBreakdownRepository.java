package com.example.donationmanagement.repositories.LoanManagement;

import com.example.donationmanagement.entities.LoanManagement.RiskBreakdown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskBreakdownRepository extends JpaRepository<RiskBreakdown, Long> {
}
