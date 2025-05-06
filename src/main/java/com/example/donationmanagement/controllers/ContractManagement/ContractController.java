package com.example.donationmanagement.controllers.ContractManagement;

import com.example.donationmanagement.entities.ContractManagement.Contract;
import com.example.donationmanagement.entities.UserManagement.User;
import com.example.donationmanagement.repositories.UserManagement.UserRepository;
import com.example.donationmanagement.services.ContractManagement.ContractService;
import com.example.donationmanagement.services.ContractManagement.IContractService;
import com.example.donationmanagement.services.UserManagement.JwtService;
import com.example.donationmanagement.services.UserManagement.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Gestion Contract")
@RestController
@RequestMapping("/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    /**
     * 🔹 Ajoute un nouveau contrat.
     */
    @PostMapping("/add")
    public ResponseEntity<Contract> addContract(@Valid @RequestBody Contract contract, HttpServletRequest request) {
        // Extraire le token de la requête
        String token = extractToken(request);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 Unauthorized si le token est absent
        }

        // Extraire l'utilisateur authentifié à partir du token
        Optional<User> authenticatedUser = userService.getAuthenticatedUser(request);

        // Utilisation de JwtService
        if (authenticatedUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 Unauthorized si l'utilisateur est non authentifié
        }

        // Passer l'utilisateur authentifié au service
        contract.setUser(authenticatedUser.get());

        // Ajouter le contrat via le service
        Contract savedContract = contractService.add(contract, request);

        // Retourner la réponse
        return ResponseEntity.ok(savedContract);
    }

    // Méthode pour extraire le token de l'en-tête Authorization
    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);  // Extraire le token après "Bearer "
        }
        return null;
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
    @GetMapping("/user/{userId}")
    public List<Contract> getContractsByUser(@PathVariable Long userId) {
        return contractService.getContractsByUserId(userId);
    }
    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> cancelContract(@PathVariable Long id) {
        try {
            String result = contractService.cancelContract(id);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


}