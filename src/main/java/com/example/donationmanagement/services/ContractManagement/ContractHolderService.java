package com.example.donationmanagement.services.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.ContractHolder;
import com.example.donationmanagement.repositories.ContractManagement.ContractHolderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j

public class ContractHolderService implements IContractHolderService {
    @Autowired
    private ContractHolderRepository contractholderRepository;
    @Override
    public ContractHolder add(ContractHolder contractholder){
        log.info("Adding contractholder:{}",contractholder);
        return contractholderRepository.save(contractholder);

    }
    @Override
    public ContractHolder update(ContractHolder contractholder){
        log.info ("Updating contractholder: {}",contractholder);
        return contractholderRepository.save(contractholder);
    }
    @Override
    public void  remove(long id){
        log.info ("Removing contractholder: {}",id);
        contractholderRepository.deleteById(id);

    }
    @Override
    public ContractHolder getById (long id){
        return contractholderRepository.findById(id).orElse(null);
    }
    @Override
    public List<ContractHolder> getAll() {
        log.info("Fetching all contractholders");
        return contractholderRepository.findAll();
    }


}