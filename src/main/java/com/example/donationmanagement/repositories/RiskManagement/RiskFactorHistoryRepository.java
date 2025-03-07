package com.example.donationmanagement.repositories.RiskManagement;

import com.example.donationmanagement.entities.RiskManagement.RiskFactorHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RiskFactorHistoryRepository extends JpaRepository<RiskFactorHistory, Long> {
    List<RiskFactorHistory> findByUserId(Long userId); // Récupère l'historique des facteurs de risque d'un utilisateur
}