package com.example.donationmanagement.controllers.DonationManagement;

import com.example.donationmanagement.entities.ContractManagement.Contract;
import com.example.donationmanagement.entities.DonationManagement.Donation;
import com.example.donationmanagement.entities.UserManagement.User;
import com.example.donationmanagement.repositories.UserManagement.UserRepository;
import com.example.donationmanagement.services.DonationManagement.IDonationService;
import com.example.donationmanagement.services.UserManagement.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/donations")
public class DonationController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IDonationService donationService;
    // Méthode pour extraire le token de l'en-tête Authorization
    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);  // Extraire le token après "Bearer "
        }
        return null;
    }
    @PostMapping("/add")
    public ResponseEntity<Donation> addDonation(@RequestBody Donation donation, HttpServletRequest request) {
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
        donation.setUser(authenticatedUser.get());

        // Ajouter le contrat via le service
        Donation savedDonation = donationService.add(donation, request);

        // Retourner la réponse
        return ResponseEntity.ok(savedDonation);
    }
    @GetMapping
    public Page<Donation> getAllDonations(Pageable pageable) {
        return donationService.getAllDonations(pageable);
    }
    @PutMapping("/update")
    public Donation updateDonation(@RequestBody Donation donation) {
        return donationService.update(donation);
    }

    @DeleteMapping("/remove/{donation_id}")
    public void removeDonation(@PathVariable Long donation_id) {
        donationService.remove(donation_id);
    }

    @GetMapping("/get/{donation_id}")
    public Donation getDonationById(@PathVariable Long donation_id) {
        return donationService.getById(donation_id);
    }

    @GetMapping("/all")
    public List<Donation> getAllDonations() {
        return donationService.getAll();
    }
    @GetMapping("/donor/{donorId}")
    public Page<Donation> getDonationsByDonorId(@PathVariable int donor_id, Pageable pageable) {
        return donationService.getDonationsByDonorId(donor_id, pageable);
    }

    @GetMapping("/amount-range")
    public Page<Donation> getDonationsByAmountRange(
            @RequestParam double minAmount,
            @RequestParam double maxAmount,
            Pageable pageable) {
        return donationService.getDonationsByAmountRange(minAmount, maxAmount, pageable);
    }
}
