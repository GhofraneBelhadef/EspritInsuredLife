package com.example.donationmanagement.services.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.ContractAccounting;

import java.util.List;

public interface IContractAccountingService {
    ContractAccounting add(ContractAccounting contractaccounting);

    /*Contract_Accounting updateTotalCapital(int matriculeFiscale);*/

    /*Contract_Accounting updateIndemnitesVersees(int matriculeFiscale, float montant);*/

    /* float getProfit(int matriculeFiscale);*/
    List<ContractAccounting> getAll();
    ContractAccounting getById(long id);
    ContractAccounting update(ContractAccounting contract_accounting);
}