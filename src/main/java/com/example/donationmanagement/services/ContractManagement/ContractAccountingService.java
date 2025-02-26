
package com.example.donationmanagement.services.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.ContractAccounting;
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

    private static final float TAUX_VIE = 0.05f;
    private static final float TAUX_NON_VIE = 0.07f;

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
        log.info(" Recherche du ContractAccounting avec matricule : {}", matriculeFiscale);

        return contractAccountingRepository.findByMatriculeFiscale(matriculeFiscale)
                .map(accounting -> {
                    log.info(" Matricule trouvé, mise à jour des indemnités...");

                    // 🔹 Calcul des indemnités
                    float indemniteCalculee = calculerIndemnites(matriculeFiscale, accounting);
                    accounting.setIndemnites_versees(indemniteCalculee);
                    accounting.setUpdated_at(new Date());

                    log.info(" Sauvegarde des nouvelles indemnités : {}", indemniteCalculee);
                    return contractAccountingRepository.save(accounting);
                })
                .orElseThrow(() -> {
                    log.error(" Matricule non trouvé en base !");
                    return new RuntimeException("ContractAccounting non trouvé pour matricule " + matriculeFiscale);
                });
    }

    private float calculerIndemnites(int matriculeFiscale, ContractAccounting accounting) {
        float provisionsTechniques = accounting.getProvisionsTechniques();
        float totalCapital = accounting.getTotal_capital();

        if (provisionsTechniques <= 0) {
            throw new IllegalStateException("Provisions techniques incorrectes ou nulles !");
        }

        float montant;
        float taux;  // Définir un taux basé sur le type d'assurance

        if (matriculeFiscale == ContractAccounting.MATRICULE_FISCALE_VIE) {
            taux = 0.15f; // Exemple : 15% des provisions pour l’assurance vie
        } else if (matriculeFiscale == ContractAccounting.MATRICULE_FISCALE_NON_VIE) {
            taux = 0.25f; // Exemple : 25% des provisions pour l’assurance non-vie
        } else {
            throw new IllegalArgumentException("Matricule fiscal non reconnu !");
        }

        // 🟢 Calcul des indemnités basées sur les provisions techniques et le taux défini
        montant = provisionsTechniques * taux;

        // 🔹 On s'assure que les indemnités ne dépassent pas le totalCapital
        if (montant > totalCapital) {
            montant = totalCapital;  // Sécurité pour éviter de payer plus que le capital disponible
        }

        return montant;
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
        float provisions = accounting.getProvisionsTechniques();

        // 🔹 Frais de gestion (exemple : 5% des primes totales)
        float fraisGestion = totalPrimes * 0.05f;

        // Calcul du bénéfice : Primes encaissées - (Indemnités + Provisions + Frais)
        float benefice = totalPrimes - (indemnites + provisions + fraisGestion);

        return benefice;
    }

    @Override
    public float calculerBeneficeTotal() {
        float beneficeVie = calculerBenefice(ContractAccounting.MATRICULE_FISCALE_VIE);
        float beneficeNonVie = calculerBenefice(ContractAccounting.MATRICULE_FISCALE_NON_VIE);

        return beneficeVie + beneficeNonVie;
    }
}
