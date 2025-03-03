package com.example.donationmanagement.repositories.RiskManagement;

import com.example.donationmanagement.entities.RiskManagement.RiskAssessment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiskAssessmentRepository extends JpaRepository<RiskAssessment, Long> {
    Page<RiskAssessment> findAll(Pageable pageable);
}
