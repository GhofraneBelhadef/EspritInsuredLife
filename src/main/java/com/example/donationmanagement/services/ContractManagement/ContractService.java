package com.example.donationmanagement.services.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.Contract;
import com.example.donationmanagement.repositories.ContractManagement.ContractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class ContractService implements IContractService {
    @Autowired
    private ContractRepository contractRepository;
    @Override
    public Contract add(Contract contract){
        log.info("Adding contract:{}",contract);
        return contractRepository.save(contract);

    }
    @Override
    public Contract update(Contract contract){
        log.info ("Updating contract: {}",contract);
        return contractRepository.save(contract);
    }
    @Override
    public void  remove(long id){
        log.info ("Removing contract: {}",id);
        contractRepository.deleteById(id);

    }
    @Override
    public Contract getById (long id){
        return contractRepository.findById(id).orElse(null);
    }
    @Override
    public List<Contract> getAll() {
        log.info("Fetching all contracts");
        return contractRepository.findAll();
    }
}
