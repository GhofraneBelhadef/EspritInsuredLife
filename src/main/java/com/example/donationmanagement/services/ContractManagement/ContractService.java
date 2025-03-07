package com.example.donationmanagement.services.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.Contract;
import com.example.donationmanagement.entities.ContractManagement.ContractAccounting;
import com.example.donationmanagement.entities.ContractManagement.ProvisionsTechniques;
import com.example.donationmanagement.entities.UserManagement.User;
import com.example.donationmanagement.repositories.ContractManagement.ContractAccountingRepository;
import com.example.donationmanagement.repositories.ContractManagement.ContractRepository;
import com.example.donationmanagement.repositories.ContractManagement.ProvisionsTechniquesRepository;
import com.example.donationmanagement.repositories.UserManagement.UserRepository;
import com.example.donationmanagement.services.UserManagement.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.donationmanagement.entities.ContractManagement.Contract.Insurrance_Type.Life_Insurance;
import static com.example.donationmanagement.entities.ContractManagement.Contract.Insurrance_Type.Non_lifeinsurance;

@Service
@Slf4j
public class ContractService implements IContractService {
    @Autowired
    private SinistraliteService sinistraliteService;
    @Autowired
    private MortalityTableService mortalityTableService;
    @Autowired
    private ProvisionsTechniquesRepository provisionsTechniquesRepository;
    @Autowired
    private ContractAccountingService contractAccountingService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;


    private final ContractRepository contractRepository;
    private final ContractAccountingRepository contractAccountingRepository;

    public ContractService(ContractRepository contractRepository, ContractAccountingRepository contractAccountingRepository) {
        this.contractRepository = contractRepository;
        this.contractAccountingRepository = contractAccountingRepository;
    }
    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");  // Récupérer l'en-tête Authorization
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);  // Extraire le token après le mot "Bearer "
        }
        return null;  // Si le token n'est pas présent ou ne commence pas par "Bearer "
    }
    public Contract add(Contract contract, HttpServletRequest request) {
        // Récupérer l'utilisateur authentifié
        Optional<User> authenticatedUser = getAuthenticatedUser(request);
        if (authenticatedUser.isEmpty()) {
            throw new RuntimeException("Utilisateur non authentifié !");
        }
        User user = authenticatedUser.get();
        contract.setUser(user); // Associer l'utilisateur au contrat

        if (contract.getInsurrance_type() == null) {
            throw new IllegalArgumentException("Le type d'assurance ne peut pas être nul !");
        }

        log.info("Ajout d'un nouveau contrat avec insurance type: {}", contract.getInsurrance_type());

        int matriculeFiscale = switch (contract.getInsurrance_type()) {
            case Life_Insurance -> 1001;
            case Non_lifeinsurance -> 2002;
            default -> throw new IllegalArgumentException("Type d'assurance inconnu !");
        };

        Optional<ContractAccounting> optionalAccounting = contractAccountingRepository.findByMatriculeFiscale(matriculeFiscale);
        if (optionalAccounting.isEmpty()) {
            throw new RuntimeException("Aucun ContractAccounting trouvé pour le matricule " + matriculeFiscale);
        }

        ContractAccounting accounting = optionalAccounting.get();
        contract.setContractAccounting(accounting);

        // 🔹 Étape 1 : Sauvegarder d'abord le contrat
        contract = contractRepository.save(contract);
        log.info("Contrat sauvegardé avec ID: {}", contract.getContract_id());

        // 🔹 Étape 2 : Vérifier si une provision existe déjà, sinon la créer
        Optional<ProvisionsTechniques> existingProvision = provisionsTechniquesRepository.findByContract(contract);
        float provision = calculateProvision(contract);

        ProvisionsTechniques provisionsTechniques = existingProvision.orElse(new ProvisionsTechniques());
        provisionsTechniques.setProvision(provision);
        provisionsTechniques.setContract(contract);
        provisionsTechniques.setCreatedAt(new Date());

        provisionsTechniques = provisionsTechniquesRepository.save(provisionsTechniques);
        contract.setProvisionsTechniques(provisionsTechniques);
        log.info("Provision technique sauvegardée avec ID: {}", provisionsTechniques.getId());

        // 🔹 Étape 3 : Mise à jour du total capital et total des provisions
        accounting.updateTotalCapital();
        contractAccountingService.updateTotalProvisions(accounting);
        contractAccountingRepository.save(accounting);
        log.info("Total capital et provisions mis à jour pour l'accounting ID: {}", accounting.getContract_accounting_id());

        return contract;
    }

    public Optional<User> getAuthenticatedUser(HttpServletRequest request) {
        String token = extractToken(request);
        if (token != null) {
            Long userId = jwtService.extractUserId(token);
            return userRepository.findById(userId); // Recherche l'utilisateur par ID
        }
        return Optional.empty();
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
        return contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrat non trouvé avec ID: " + id));
    }

    @Override
    public List<Contract> getAll() {
        log.info("Fetching all contracts");
        return contractRepository.findAll();
    }




    public boolean hasContractsForMatricule(int matriculeFiscale) {
        return !contractRepository.findByContractAccounting_MatriculeFiscale(matriculeFiscale).isEmpty();
    }

    private float calculateProvision(Contract contract) {
        float provision = switch (contract.getInsurrance_type()) {
            case Life_Insurance -> calculateLifeInsuranceProvision(contract);
            case Non_lifeinsurance -> calculateNonLifeInsuranceProvision(contract);
        };

        log.info("Provision calculée pour le contrat ID {}: {}", contract.getContract_id(), provision);
        return provision;
    }


    private float calculateLifeInsuranceProvision(Contract contract) {
        int age = contract.getInsuredAge();
        float monthlyPrice = contract.getMonthly_price();

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(contract.getPolicy_inception_date());

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(contract.getExpiration_date());

        int yearsRemaining = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);

        log.info("🟢 Calcul Life Insurance Provision: Age={} | Years Remaining={} | Monthly Price={}",
                age, yearsRemaining, monthlyPrice);

        float provision = 0.0f;
        for (int t = 0; t < yearsRemaining; t++) {
            float probMort = mortalityTableService.getProbabilityOfDeath(age + t);
            log.info("   🔹 Année {}: probMort={} | Prime Annuelle={}", t, probMort, monthlyPrice * 12);
            provision += probMort * monthlyPrice * 12;
        }

        log.info("✅ Provision calculée = {}", provision);
        return provision;
    }

    private float calculateNonLifeInsuranceProvision(Contract contract) {
        String category = contract.getCategory_contract().name();
        float tauxSinistralite = sinistraliteService.getTauxSinistralite(category);

        return contract.getMonthly_price() * 12 * tauxSinistralite;
    }
    @Override
    public Page<Contract> getAllContracts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);  // Créer un objet Pageable pour la pagination
        return contractRepository.findAll(pageable);  // Retourne une page de contrats
    }

}






