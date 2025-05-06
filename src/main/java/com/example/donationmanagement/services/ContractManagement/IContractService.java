package com.example.donationmanagement.services.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.Contract;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IContractService {
    public Contract add(Contract contract, HttpServletRequest request);
    Contract update(Contract contract);
    void remove(long id);
    Contract getById(long id);
    List<Contract> getAll();
    Page<Contract> getAllContracts(int page, int size);
    List<Contract> getContractsByUserId(Long userId);
    String cancelContract(Long contractId);

}
