package com.example.donationmanagement.repositories.ContractManagement;

import org.springframework.data.repository.query.Param;
import com.example.donationmanagement.entities.ContractManagement.ContractAccounting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;

public interface ContractAccountingRepository extends JpaRepository<ContractAccounting, Long> {

    Optional<ContractAccounting> findByMatriculeFiscale(int matriculeFiscale);

    @Query("SELECT c FROM ContractAccounting c LEFT JOIN FETCH c.provisionsTechniques WHERE c.matriculeFiscale = :matriculeFiscale")
    List<ContractAccounting> findWithProvisionsByMatriculeFiscale(@Param("matriculeFiscale") int matriculeFiscale);

}
