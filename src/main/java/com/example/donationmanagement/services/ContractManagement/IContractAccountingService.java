package com.example.donationmanagement.services.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.ContractAccounting;

import java.util.List;

public interface IContractAccountingService {
    ContractAccounting add(ContractAccounting contractaccounting);

    ContractAccounting updateTotalCapital(int matriculeFiscale);

    ContractAccounting updateIndemnitesVersees(int matriculeFiscale);

     float getProfit(int matriculeFiscale);
    List<ContractAccounting> getAll();
    ContractAccounting getById(long id);
    ContractAccounting update(ContractAccounting contract_accounting);
    float calculerBenefice(int matriculeFiscale); // ✅ Vérifie bien ce type de retour
    float calculerBeneficeTotal();


}
