package com.example.donationmanagement.services.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.Contract;

import java.util.List;

public interface IContractService {
    Contract add (Contract contract );
    Contract update(Contract contract);
    void remove(long id);
    Contract getById(long id);
    List<Contract> getAll();

}
