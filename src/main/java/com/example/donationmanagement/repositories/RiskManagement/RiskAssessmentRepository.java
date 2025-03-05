package com.example.donationmanagement.repositories.RiskManagement;

import com.example.donationmanagement.entities.RiskManagement.RiskAssessment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RiskAssessmentRepository extends JpaRepository<RiskAssessment, Long> {
    Page<RiskAssessment> findAll(Pageable pageable);
    @Query("SELECT r FROM RiskAssessment r " +
            "WHERE (:search IS NULL OR " +
            "CAST(r.UserId AS string) LIKE %:search% OR " +
            "CAST(r.RiskScore AS string) LIKE %:search% OR " +
            "CAST(r.Price AS string) LIKE %:search% OR " +
            "LOWER(CAST(r.RiskType AS string)) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<RiskAssessment> searchRiskAssessments(@Param("search") String search);

}
