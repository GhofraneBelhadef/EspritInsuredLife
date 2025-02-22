package com.example.donationmanagement.repositories.LoanManagement;

import com.example.donationmanagement.entities.LoanManagement.Customer; // Fixed import
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
