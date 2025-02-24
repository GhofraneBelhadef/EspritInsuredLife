package com.example.donationmanagement.services.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.ContractHolder;

import java.util.List;

public interface IContractHolderService {
    ContractHolder add (ContractHolder contract_holder );
    ContractHolder update(ContractHolder contract_holder);
    void remove(long id);
    ContractHolder getById(long id);
    List<ContractHolder> getAll();
}
