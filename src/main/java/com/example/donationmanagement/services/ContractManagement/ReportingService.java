
package com.example.donationmanagement.services.ContractManagement;
import com.example.donationmanagement.repositories.ContractManagement.ContractRepository;
import com.example.donationmanagement.repositories.ContractManagement.ContractAccountingRepository;
import com.example.donationmanagement.services.ContractManagement.SinistraliteService;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportingService {

    private final ContractRepository contractRepository;
    private final ContractAccountingRepository contractAccountingRepository;
    private final SinistraliteService sinistraliteService;

    public ReportingService(ContractRepository contractRepository, ContractAccountingRepository contractAccountingRepository, SinistraliteService sinistraliteService) {
        this.contractRepository = contractRepository;
        this.contractAccountingRepository = contractAccountingRepository;
        this.sinistraliteService = sinistraliteService;
    }

    // 🔹 1. Nombre total de contrats
    public long getTotalContracts() {
        return contractRepository.count();
    }

    // 🔹 2. Répartition des contrats par type (Vie / Non-Vie)
    public Map<String, Long> getContractTypeDistribution() {
        return contractRepository.findAll().stream()
                .collect(Collectors.groupingBy(contract -> contract.getInsurrance_type().name(), Collectors.counting()));
    }

    // 🔹 3. Somme totale du capital assuré
    public float getTotalCapital() {
        return (float) contractRepository.findAll().stream()
                .mapToDouble(contract -> contract.getMonthly_price() * 12)
                .sum();
    }

    // 🔹 4. Montant total des provisions techniques
    public float getTotalProvisions() {
        return (float) contractAccountingRepository.findAll().stream()
                .mapToDouble(accounting -> accounting.getTotalProvisions())
                .sum();
    }

    // 🔹 5. Moyenne des taux de sinistralité
    public float getAverageSinistralite() {
        return sinistraliteService.getAverageSinistralite();
    }
}

