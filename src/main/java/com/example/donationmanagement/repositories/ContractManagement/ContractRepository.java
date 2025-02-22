package com.example.donationmanagement.repositories.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.Contract;
import com.example.donationmanagement.entities.DonationManagement.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}
