package com.example.donationmanagement.services.RiskManagement;

public interface IRiskFactorHistoryService {
    public void addRiskFactorHistory(Long userId, Long riskFactorId, int factorValue, String description);
}
