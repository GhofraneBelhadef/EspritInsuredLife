package com.example.donationmanagement.repositories.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.Contract;
import com.example.donationmanagement.entities.ContractManagement.ProvisionsTechniques;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProvisionsTechniquesRepository extends JpaRepository<ProvisionsTechniques, Long> {
    Optional<ProvisionsTechniques> findByContract(Contract contract);

    @Query("SELECT SUM(p.provision) FROM ProvisionsTechniques p")
    Float sumAllProvisions();
    @Query("SELECT COALESCE(SUM(p.provision), 0) FROM ProvisionsTechniques p WHERE p.contract.contractAccounting.id = :accountingId")
    float sumProvisionsByAccountingId(@Param("accountingId") Long accountingId);
}
