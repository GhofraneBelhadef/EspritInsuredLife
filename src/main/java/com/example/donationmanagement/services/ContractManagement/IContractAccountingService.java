package com.example.donationmanagement.services.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.Contract_Accounting;

import java.util.List;

public interface IContractAccountingService {
    Contract_Accounting add(Contract_Accounting contractaccounting);

    /*Contract_Accounting updateTotalCapital(int matriculeFiscale);*/

    /*Contract_Accounting updateIndemnitesVersees(int matriculeFiscale, float montant);*/

    /* float getProfit(int matriculeFiscale);*/
    List<Contract_Accounting> getAll();
    Contract_Accounting getById(long id);
    Contract_Accounting update(Contract_Accounting contract_accounting);
}
