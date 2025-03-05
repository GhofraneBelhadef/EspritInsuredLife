package com.example.donationmanagement.repositories.ClaimManagement;

import com.example.donationmanagement.entities.ClaimManagement.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByStatus(String status);
}
