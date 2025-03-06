package com.example.donationmanagement.controllers.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.Contract;
import com.example.donationmanagement.services.ContractManagement.IContractService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Gestion Contract")
@RestController
@RequestMapping("/contracts")
public class ContractController {

    @Autowired
    private IContractService contractService;

    /**
     * 🔹 Ajoute un nouveau contrat.
     */
    @PostMapping("/add")
    public ResponseEntity<Contract> addContract(@Valid @RequestBody Contract contract) {
        if (contract.getInsurrance_type() == null) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request si le type d'assurance est null
        }
        Contract savedContract = contractService.add(contract);
        return ResponseEntity.ok(savedContract);
    }

    /**
     * 🔹 Met à jour un contrat existant.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Contract> updateContract(@PathVariable long id, @RequestBody Contract contract) {
        Contract existingContract = contractService.getById(id);
        if (existingContract == null) {
            return ResponseEntity.notFound().build(); // 404 si le contrat n'existe pas
        }
        contract.setContract_id(id); // S'assurer que l'ID est bien conservé
        Contract updatedContract = contractService.update(contract);
        return ResponseEntity.ok(updatedContract);
    }

    /**
     * 🔹 Supprime un contrat par ID.
     */
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeContract(@PathVariable long id) {
        Contract existingContract = contractService.getById(id);
        if (existingContract == null) {
            return ResponseEntity.notFound().build(); // 404 si le contrat n'existe pas
        }
        contractService.remove(id);
        return ResponseEntity.noContent().build(); // 204 No Content après suppression
    }

    /**
     * 🔹 Récupère tous les contrats.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Contract>> getAllContracts() {
        List<Contract> contracts = contractService.getAll();
        return ResponseEntity.ok(contracts);
    }

    /**
     * 🔹 Récupère un contrat par ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Contract> getContractById(@PathVariable long id) {
        Contract contract = contractService.getById(id);
        return (contract != null) ? ResponseEntity.ok(contract) : ResponseEntity.notFound().build();
    }
    @GetMapping("/contracts/all")
    public ResponseEntity<Page<Contract>> getAllContracts(
            @RequestParam(defaultValue = "0") int page,      // Numéro de la page
            @RequestParam(defaultValue = "10") int size) {   // Taille de la page

        Page<Contract> contracts = contractService.getAllContracts(page, size);
        return ResponseEntity.ok(contracts);  // Retourne la page des contrats
    }

}
