package com.example.donationmanagement.repositories.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.ContractAccounting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractAccountingRepository extends JpaRepository<ContractAccounting,Long> {
    Optional<ContractAccounting> findByMatriculeFiscale(int matriculeFiscale);

}