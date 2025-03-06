package com.example.donationmanagement.services.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.ContractAccounting;
import com.example.donationmanagement.repositories.ContractManagement.ContractAccountingRepository;
import com.example.donationmanagement.repositories.ContractManagement.ContractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ContractAccountingService implements IContractAccountingService{
    @Autowired
    private ContractAccountingRepository contractAccountingRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Override
    public ContractAccounting add(ContractAccounting contractAccounting) {
        log.info("Adding contractholder:{}",contractAccounting);
        return contractAccountingRepository.save(contractAccounting);
    }

    /* @Override
     public Contract_Accounting updateTotalCapital(int matriculeFiscale) {
         float totalCapital = contractRepository.sumMonthlyPricesOfActiveContracts();
         return contractAccountingRepository.findByMatriculeFiscale(matriculeFiscale).map(accounting -> {
             accounting.setTotal_capital(totalCapital);
             accounting.setUpdated_at(new Date());
             return contractAccountingRepository.save(accounting);
         }).orElseThrow(() -> new RuntimeException("ContractAccounting non trouvé"));
     }*/
    @Override
    public ContractAccounting update(ContractAccounting contract_accounting){
        log.info ("Updating contractholder: {}",contract_accounting);
        return contractAccountingRepository.save(contract_accounting);
    }

  /*  @Override
    public Contract_Accounting updateIndemnitesVersees(int matriculeFiscale, float montant) {
        return contractAccountingRepository.findByMatriculeFiscale(matriculeFiscale).map(accounting -> {
            accounting.setIndemnites_versees(accounting.getIndemnites_versees() + montant);
            accounting.setUpdated_at(new Date());
            return contractAccountingRepository.save(accounting);
        }).orElseThrow(() -> new RuntimeException("ContractAccounting non trouvé"));
    }*/

    /* @Override
     public float getProfit(int matriculeFiscale) {
         return contractAccountingRepository.findByMatriculeFiscale(matriculeFiscale)
                 .map(accounting -> accounting.getTotal_capital() - accounting.getIndemnites_versees())
                 .orElseThrow(() -> new RuntimeException("ContractAccounting non trouvé"));
     }*/
    @Override
    public List<ContractAccounting> getAll() {
        log.info("Fetching all contractholders");
        return contractAccountingRepository.findAll();
    }
    @Override
    public ContractAccounting getById (long id){
        return contractAccountingRepository.findById(id).orElse(null);
    }

}