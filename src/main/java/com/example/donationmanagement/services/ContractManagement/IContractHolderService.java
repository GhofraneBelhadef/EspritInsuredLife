package com.example.donationmanagement.services.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.Contract_Holder;

import java.util.List;

public interface IContractHolderService {
    Contract_Holder add (Contract_Holder contract_holder );
    Contract_Holder update(Contract_Holder contract_holder);
    void remove(long id);
    Contract_Holder getById(long id);
    List<Contract_Holder> getAll();
}
