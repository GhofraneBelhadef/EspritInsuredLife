package com.example.donationmanagement.repositories.RiskManagement;

import com.example.donationmanagement.entities.RiskManagement.RiskFactors;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RiskFactorsRepository extends JpaRepository<RiskFactors, Long> {
}
