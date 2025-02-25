package com.example.donationmanagement.repositories.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract,Long> {

    @Query("SELECT COALESCE(SUM(c. monthly_price), 0) FROM Contract c WHERE c.status = 'Active'")
    float sumMonthlyPricesOfActiveContracts();
}
