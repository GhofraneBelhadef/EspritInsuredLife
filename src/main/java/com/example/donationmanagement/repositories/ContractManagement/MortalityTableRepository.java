package com.example.donationmanagement.repositories.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.MortalityTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MortalityTableRepository extends JpaRepository<MortalityTable, Long> {
    MortalityTable findByAge(int age);
}
