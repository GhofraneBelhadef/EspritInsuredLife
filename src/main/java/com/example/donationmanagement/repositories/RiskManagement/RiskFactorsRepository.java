package com.example.donationmanagement.repositories.RiskManagement;

import com.example.donationmanagement.entities.RiskManagement.RiskAssessment;
import com.example.donationmanagement.entities.RiskManagement.RiskFactors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RiskFactorsRepository extends JpaRepository<RiskFactors, Long> {
    Page<RiskFactors> findAll(Pageable pageable);
}
