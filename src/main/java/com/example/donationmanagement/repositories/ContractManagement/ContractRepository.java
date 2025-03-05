package com.example.donationmanagement.repositories.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.Contract;
import com.example.donationmanagement.entities.ContractManagement.ContractAccounting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract,Long> {
    @Query("SELECT COALESCE(SUM(c.monthly_price), 0) FROM Contract c WHERE c.contractAccounting.matriculeFiscale = :matriculeFiscale AND c.status = 'Active'")
    float sumMonthlyPricesByMatriculeFiscale(@Param("matriculeFiscale") int matriculeFiscale);
    List<Contract> findByContractAccounting_MatriculeFiscale(int matriculeFiscale);


   }
