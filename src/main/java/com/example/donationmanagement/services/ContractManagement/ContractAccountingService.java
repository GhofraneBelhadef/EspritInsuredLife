package com.example.donationmanagement.services.ContractManagement;
import com.example.donationmanagement.entities.ContractManagement.ContractAccounting;
import com.example.donationmanagement.entities.ContractManagement.ProvisionsTechniques;
import com.example.donationmanagement.repositories.ContractManagement.ContractAccountingRepository;
import com.example.donationmanagement.repositories.ContractManagement.ContractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ContractAccountingService implements IContractAccountingService {

    @Autowired
    private ContractAccountingRepository contractAccountingRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Override
    public ContractAccounting add(ContractAccounting contractAccounting) {
        log.info("Ajout d'un ContractAccounting avec matricule fiscale: {}", contractAccounting.getMatriculeFiscale());
        contractAccounting.setCreated_at(new Date());
        contractAccounting.setUpdated_at(new Date());
        contractAccounting = contractAccountingRepository.save(contractAccounting);
        return updateContractAccounting(contractAccounting.getContract_accounting_id());
    }

    @Override
    public ContractAccounting update(ContractAccounting contractAccounting) {
        log.info("Mise à jour de ContractAccounting: {}", contractAccounting);
        contractAccounting.setUpdated_at(new Date());
        return contractAccountingRepository.save(contractAccounting);
    }

    public ContractAccounting updateContractAccounting(Long contractId) {
        return contractAccountingRepository.findById(contractId)
                .map(accounting -> {
                    accounting.updateCalculatedFields();
                    accounting.setUpdated_at(new Date());
                    return contractAccountingRepository.save(accounting);
                })
                .orElseThrow(() -> new RuntimeException("ContractAccounting non trouvé avec l'ID: " + contractId));
    }

    @Override
    public ContractAccounting updateTotalCapital(int matriculeFiscale) {
        log.info("Mise à jour du total capital pour matricule fiscale: {}", matriculeFiscale);
        float totalCapital = contractRepository.sumMonthlyPricesByMatriculeFiscale(matriculeFiscale);
        return contractAccountingRepository.findByMatriculeFiscale(matriculeFiscale)
                .map(accounting -> {
                    accounting.setTotal_capital(totalCapital);
                    accounting.setUpdated_at(new Date());
                    return contractAccountingRepository.save(accounting);
                })
                .orElseThrow(() -> new RuntimeException("ContractAccounting non trouvé pour matricule " + matriculeFiscale));
    }
    @Override
    public ContractAccounting updateIndemnitesVersees(int matriculeFiscale) {
        log.info("Recherche du ContractAccounting avec matricule : {}", matriculeFiscale);

        return contractAccountingRepository.findByMatriculeFiscale(matriculeFiscale)
                .map(accounting -> {
                    log.info("Matricule trouvé, mise à jour des indemnités...");

                    // 🔹 Mise à jour des provisions avant le calcul
                    updateTotalProvisions(accounting);

                    // 🔹 Calcul des indemnités
                    float indemniteCalculee = calculerIndemnites(matriculeFiscale, accounting);
                    accounting.setIndemnites_versees(indemniteCalculee);
                    accounting.setUpdated_at(new Date());

                    log.info("Sauvegarde des nouvelles indemnités : {}", indemniteCalculee);
                    return contractAccountingRepository.save(accounting);
                })
                .orElseThrow(() -> {
                    log.error("Matricule non trouvé en base !");
                    return new RuntimeException("ContractAccounting non trouvé pour matricule " + matriculeFiscale);
                });
    }


    @Override
    public float getProfit(int matriculeFiscale) {
        return contractAccountingRepository.findByMatriculeFiscale(matriculeFiscale)
                .map(accounting -> accounting.getTotal_capital() - accounting.getIndemnites_versees())
                .orElse(0.0f);
    }

    @Override
    public List<ContractAccounting> getAll() {
        return contractAccountingRepository.findAll();
    }

    @Override
    public ContractAccounting getById(long id) {
        return contractAccountingRepository.findById(id).orElse(null);
    }


    @Override
    public float calculerBenefice(int matriculeFiscale) {
        ContractAccounting accounting = contractAccountingRepository.findByMatriculeFiscale(matriculeFiscale)
                .orElseThrow(() -> new RuntimeException("ContractAccounting non trouvé pour matricule " + matriculeFiscale));

        float totalPrimes = contractRepository.sumMonthlyPricesByMatriculeFiscale(matriculeFiscale);
        float indemnites = accounting.getIndemnites_versees();
        float provisions = accounting.getTotalProvisions();

        float fraisGestion = totalPrimes * 0.05f;

        float benefice = totalPrimes - (indemnites + provisions + fraisGestion);

        return benefice;
    }

    @Override
    public float calculerBeneficeTotal() {
        float beneficeVie = calculerBenefice(ContractAccounting.MATRICULE_FISCALE_VIE);
        float beneficeNonVie = calculerBenefice(ContractAccounting.MATRICULE_FISCALE_NON_VIE);

        return beneficeVie + beneficeNonVie;
    }
    @Override
    public void updateTotalProvisions(ContractAccounting accounting) {
        if (accounting == null) {
            log.warn(" ContractAccounting null, impossible de mettre à jour les provisions !");
            return;
        }

        if (accounting.getProvisionsTechniques() == null || accounting.getProvisionsTechniques().isEmpty()) {
            log.warn(" Pas de provisions techniques trouvées pour ContractAccounting ID: {}", accounting.getContract_accounting_id());
            return;
        }

        float totalProvisions = (float) accounting.getProvisionsTechniques()
                .stream()
                .mapToDouble(ProvisionsTechniques::getProvision)
                .sum();

        accounting.setTotalProvisions(totalProvisions);
        contractAccountingRepository.save(accounting);

        log.info(" Total des provisions techniques mises à jour : {}", totalProvisions);
    }
    private float calculerIndemnites(int matriculeFiscale, ContractAccounting accounting) {
        float totalProvisions = accounting.getTotalProvisions();
        float totalCapital = accounting.getTotal_capital();

        if (totalProvisions <= 0) {
            throw new IllegalStateException("Provisions techniques incorrectes ou nulles !");
        }

        float montant;
        float taux;

        if (matriculeFiscale == ContractAccounting.MATRICULE_FISCALE_VIE) {
            taux = 0.15f; // Ex: 15% des provisions pour assurance vie
        } else if (matriculeFiscale == ContractAccounting.MATRICULE_FISCALE_NON_VIE) {
            taux = 0.25f; // Ex: 25% des provisions pour assurance non-vie
        } else {
            throw new IllegalArgumentException("Matricule fiscal non reconnu !");
        }

        montant = totalProvisions * taux;

        // S'assurer que l'indemnité ne dépasse pas le capital total
        if (montant > totalCapital) {
            montant = totalCapital;
        }

        return montant;
    }

}

