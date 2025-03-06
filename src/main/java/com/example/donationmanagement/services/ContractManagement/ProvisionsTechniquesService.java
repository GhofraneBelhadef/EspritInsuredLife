package com.example.donationmanagement.services.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.Contract;
import com.example.donationmanagement.entities.ContractManagement.ProvisionsTechniques;
import com.example.donationmanagement.repositories.ContractManagement.ProvisionsTechniquesRepository;
import com.example.donationmanagement.services.ContractManagement.MortalityTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class ProvisionsTechniquesService {

    @Autowired
    private ProvisionsTechniquesRepository provisionsTechniquesRepository;

    @Autowired
    private MortalityTableService mortalityTableService;

    private static final float TAUX_ACTUALISATION = 0.03f; // Exemple : 3% par an

    /**
     * Ajoute une provision technique automatiquement lorsqu'un contrat est ajouté.
     */
    public void addProvisionForContract(Contract contract) {
        if (contract.getInsurrance_type() == Contract.Insurrance_Type.Life_Insurance) {
            float provisionAmount = calculateLifeInsuranceProvision(contract);

            ProvisionsTechniques provision = new ProvisionsTechniques();
            provision.setContract(contract);
            provision.setProvision(provisionAmount);
            provision.setCreatedAt(new Date());

            provisionsTechniquesRepository.save(provision);
        }
    }

    /**
     * Calcule la provision technique pour une assurance vie.
     */
    private float calculateLifeInsuranceProvision(Contract contract) {
        int age = contract.getInsuredAge();
        int dureeRestante = getYearsBetween(contract.getPolicy_inception_date(), contract.getExpiration_date());
        float monthlyPrice = contract.getMonthly_price();
        float provision = 0.0f;

        for (int t = 1; t <= dureeRestante; t++) {
            float probMort = mortalityTableService.getProbabilityOfDeath(age + t);
            float valeurActualisee = (monthlyPrice * 12 * probMort) / (float) Math.pow(1 + TAUX_ACTUALISATION, t);
            provision += valeurActualisee;
        }

        return provision;
    }

    /**
     * Calcule le nombre d'années entre deux dates.
     */
    private int getYearsBetween(Date startDate, Date endDate) {
        long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
        return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) / 365;
    }
}
