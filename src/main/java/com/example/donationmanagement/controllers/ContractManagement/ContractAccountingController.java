package com.example.donationmanagement.controllers.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.ContractAccounting;
import com.example.donationmanagement.repositories.ContractManagement.ContractAccountingRepository;
import com.example.donationmanagement.services.ContractManagement.ContractAccountingService;
import com.example.donationmanagement.services.ContractManagement.IContractAccountingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Gestion ContractAccounting")
@RestController
@RequestMapping("/contractAccounting")
public class ContractAccountingController {
    private static final Logger log = LoggerFactory.getLogger(ContractAccountingController.class);
    @Autowired
    private IContractAccountingService contractAccountingService;
    @Autowired
    private ContractAccountingRepository contractAccountingRepository;




    @PostMapping("/add")
    public ResponseEntity<ContractAccounting> add(@Valid @RequestBody ContractAccounting contractAccounting) {
        if (contractAccounting.getMatriculeFiscale() == 0) {
            return ResponseEntity.badRequest().build();
        }
        ContractAccounting savedAccounting = contractAccountingService.add(contractAccounting);
        return ResponseEntity.ok(savedAccounting);
    }

    /**
     * 🔹 Récupère tous les ContractAccounting.
     */
    @GetMapping("/all")
    public ResponseEntity<List<ContractAccounting>> getAllContractAccounting() {
        List<ContractAccounting> accountings = contractAccountingService.getAll();
        return ResponseEntity.ok(accountings);
    }

    /**
     * 🔹 Mise à jour d'un ContractAccounting existant.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<ContractAccounting> updateContractAccounting(
            @PathVariable long id,
            @RequestBody ContractAccounting contractAccounting) {

        ContractAccounting existingAccounting = contractAccountingService.getById(id);
        if (existingAccounting == null) {
            return ResponseEntity.notFound().build();
        }

        contractAccounting.setContract_accounting_id(id);
        ContractAccounting updatedAccounting = contractAccountingService.update(contractAccounting);
        return ResponseEntity.ok(updatedAccounting);
    }

    /**
     * 🔹 Récupère un ContractAccounting par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContractAccounting> getContractAccountingById(@PathVariable long id) {
        ContractAccounting accounting = contractAccountingService.getById(id);
        return (accounting != null) ? ResponseEntity.ok(accounting) : ResponseEntity.notFound().build();
    }

    /**
     * 🔹 Met à jour le total capital pour un matricule fiscal donné.
     */
    @PutMapping("/update-total-capital/{matriculeFiscale}")
    public ResponseEntity<ContractAccounting> updateTotalCapital(@PathVariable int matriculeFiscale) {
        try {
            ContractAccounting updatedAccounting = contractAccountingService.updateTotalCapital(matriculeFiscale);
            return ResponseEntity.ok(updatedAccounting);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 🔹 Met à jour les indemnités versées pour un matricule fiscal donné.
     */
    @PutMapping("/update-indemnites/{matriculeFiscale}")
    public ResponseEntity<ContractAccounting> updateIndemnitesVersees(@PathVariable int matriculeFiscale) {
        log.info(" Requête reçue pour mise à jour des indemnités matricule : {}", matriculeFiscale);
        try {
            ContractAccounting updatedAccounting = contractAccountingService.updateIndemnitesVersees(matriculeFiscale);
            return ResponseEntity.ok(updatedAccounting);
        } catch (RuntimeException e) {
            log.error(" Erreur : {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * 🔹 Calcule le profit pour un matricule fiscal donné.
     */
    @GetMapping("/profit/{matriculeFiscale}")
    public ResponseEntity<Float> getProfit(@PathVariable int matriculeFiscale) {
        float profit = contractAccountingService.getProfit(matriculeFiscale);
        return ResponseEntity.ok(profit);
    }
    @GetMapping("/benefice/{matriculeFiscale}")
    public ResponseEntity<Float> getBenefice(@PathVariable int matriculeFiscale) {
        float benefice = contractAccountingService.calculerBenefice(matriculeFiscale);
        return ResponseEntity.ok(benefice);
    }

    /**
     * 🔹 Calcul du bénéfice total des assurances.
     */
    @GetMapping("/benefice-total")
    public ResponseEntity<Float> getBeneficeTotal() {
        float beneficeTotal = contractAccountingService.calculerBeneficeTotal();
        return ResponseEntity.ok(beneficeTotal);
    }
    @GetMapping("/total-provisions/{accountingId}")
    public ResponseEntity<Float> getTotalProvisions(@PathVariable Long accountingId) {
        // Récupérer le ContractAccounting
        ContractAccounting accounting = contractAccountingRepository.findById(accountingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ContractAccounting introuvable"));

        // Mettre à jour les provisions
        contractAccountingService.updateTotalProvisions(accounting);

        // Retourner la valeur mise à jour
        return ResponseEntity.ok(accounting.getTotalProvisions());
    }
}
