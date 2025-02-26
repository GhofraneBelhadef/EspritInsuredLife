package com.example.donationmanagement.services.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.Contract;
import com.example.donationmanagement.entities.ContractManagement.ContractAccounting;
import com.example.donationmanagement.repositories.ContractManagement.ContractAccountingRepository;
import com.example.donationmanagement.repositories.ContractManagement.ContractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ContractService implements IContractService {

    private final ContractRepository contractRepository;
    private final ContractAccountingRepository contractAccountingRepository;

    public ContractService(ContractRepository contractRepository, ContractAccountingRepository contractAccountingRepository) {
        this.contractRepository = contractRepository;
        this.contractAccountingRepository = contractAccountingRepository;
    }

    @Override
    public Contract add(Contract contract) {
        if (contract.getInsurrance_type() == null) {
            throw new IllegalArgumentException("Le type d'assurance ne peut pas être nul !");
        }

        log.info("Ajout d'un nouveau contrat avec insurance type: {}", contract.getInsurrance_type());

        int matriculeFiscale;
        if (contract.getInsurrance_type() == Contract.Insurrance_Type.Life_Insurance) {
            matriculeFiscale = 1001;
        } else if (contract.getInsurrance_type() == Contract.Insurrance_Type.Non_lifeinsurance) {
            matriculeFiscale = 2002;
        } else {
            throw new IllegalArgumentException("Type d'assurance inconnu !");
        }

        ContractAccounting accounting = contractAccountingRepository.findByMatriculeFiscale(matriculeFiscale)
                .orElseThrow(() -> new RuntimeException("Aucun ContractAccounting trouvé pour le matricule " + matriculeFiscale));

        contract.setContractAccounting(accounting);
        Contract savedContract = contractRepository.save(contract);

        // Mise à jour du total capital dans ContractAccounting
        accounting.updateTotalCapital();
        contractAccountingRepository.save(accounting);

        return savedContract;
    }

    @Override
    public Contract update(Contract contract) {
        if (!contractRepository.existsById(contract.getContract_id())) {
            throw new RuntimeException("Le contrat avec ID " + contract.getContract_id() + " n'existe pas !");
        }
        log.info("Updating contract: {}", contract);
        return contractRepository.save(contract);
    }

    @Override
    public void remove(long id) {
        if (!contractRepository.existsById(id)) {
            throw new RuntimeException("Le contrat avec ID " + id + " n'existe pas !");
        }
        log.info("Removing contract: {}", id);
        contractRepository.deleteById(id);
    }

    @Override
    public Contract getById(long id) {
        return contractRepository.findById(id).orElseThrow(() -> new RuntimeException("Contrat non trouvé avec ID: " + id));
    }

    @Override
    public List<Contract> getAll() {
        log.info("Fetching all contracts");
        return contractRepository.findAll();
    }

    public boolean hasContractsForMatricule(int matriculeFiscale) {
        return !contractRepository.findByContractAccounting_MatriculeFiscale(matriculeFiscale).isEmpty();
    }
}
