package com.example.donationmanagement.controllers.UserManagement;
import com.example.donationmanagement.entities.UserManagement.User;
import com.example.donationmanagement.repositories.UserManagement.UserRepository;
import com.example.donationmanagement.services.UserManagement.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @RequestBody User updatedUser,
                                        HttpServletRequest request,
                                        @RequestParam(value = "photo", required = false) MultipartFile photo,
                                        @RequestParam(value = "cin", required = false) MultipartFile cin,
                                        @RequestParam(value = "justificatifDomicile", required = false) MultipartFile justificatifDomicile,
                                        @RequestParam(value = "rib", required = false) MultipartFile rib,
                                        @RequestParam(value = "bulletinSalaire", required = false) MultipartFile bulletinSalaire,
                                        @RequestParam(value = "declarationSante", required = false) MultipartFile declarationSante,
                                        @RequestParam(value = "designationBeneficiaire", required = false) MultipartFile designationBeneficiaire,
                                        @RequestParam(value = "photoProfil", required = false) MultipartFile photoProfil) {

        Optional<User> authenticatedUser = userService.getAuthenticatedUser(request);

        if (authenticatedUser.isPresent() && authenticatedUser.get().getId().equals(id)) {
            return ResponseEntity.ok(userService.update(id, updatedUser, photo, cin, justificatifDomicile, rib, bulletinSalaire, declarationSante, designationBeneficiaire, photoProfil));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Accès refusé !");
        }
    }
    @PutMapping("/{id}/photo")
    public ResponseEntity<User> updatePhoto(@PathVariable Long id, @RequestParam("photo") MultipartFile photo) throws IOException {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPhotoProfil(photo.getBytes());  // Pass the byte[] directly
            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, HttpServletRequest request) {
        Optional<User> authenticatedUser = userService.getAuthenticatedUser(request);

        if (authenticatedUser.isPresent()) {
            User user = authenticatedUser.get();
            System.out.println("ID connecté: " + user.getId() + ", rôle: " + user.getRole());


            // Vérifie si l'utilisateur est ADMIN ou s'il essaie de supprimer son propre compte
            if (user.getRole().equals("ADMIN") || user.getId().equals(id)) {
                userService.delete(id);
                return ResponseEntity.ok().body("Utilisateur supprimé avec succès !");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Accès refusé !");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non authentifié !");
        }

    }
    @PutMapping("/complete-profile/{email}")
    public ResponseEntity<String> completeUserProfile(
            @PathVariable String email,
            @RequestParam("telephone") String telephone,
            @RequestParam("username") String username,
            @RequestParam("datenaissance") String datenaissance,

            @RequestParam(value = "cin", required = false) MultipartFile cin,
            @RequestParam(value = "justificatifDomicile", required = false) MultipartFile justificatifDomicile,
            @RequestParam(value = "rib", required = false) MultipartFile rib,
            @RequestParam(value = "bulletinSalaire", required = false) MultipartFile bulletinSalaire,
            @RequestParam(value = "declarationSante", required = false) MultipartFile declarationSante,
            @RequestParam(value = "designationBeneficiaire", required = false) MultipartFile designationBeneficiaire,
            @RequestParam(value = "photoProfil", required = false) MultipartFile photoProfil
    ) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        user.setTelephone(telephone);
        user.setUsername(username);
        user.setDateNaissance(LocalDate.parse(datenaissance));

        try {
            if (cin != null && !cin.isEmpty()) user.setCin(cin.getBytes());
            if (justificatifDomicile != null && !justificatifDomicile.isEmpty()) user.setJustificatifDomicile(justificatifDomicile.getBytes());
            if (rib != null && !rib.isEmpty()) user.setRib(rib.getBytes());
            if (bulletinSalaire != null && !bulletinSalaire.isEmpty()) user.setBulletinSalaire(bulletinSalaire.getBytes());
            if (declarationSante != null && !declarationSante.isEmpty()) user.setDeclarationSante(declarationSante.getBytes());
            if (designationBeneficiaire != null && !designationBeneficiaire.isEmpty()) user.setDesignationBeneficiaire(designationBeneficiaire.getBytes());
            if (photoProfil != null && !photoProfil.isEmpty()) user.setPhotoProfil(photoProfil.getBytes());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour des fichiers.");
        }

        user.setProfilComplet(true);
        userRepository.save(user);

        return ResponseEntity.ok("Profil complété avec succès !");
    }
    @GetMapping("/donors")
    public ResponseEntity<List<User>> getAllDonors() {
        return ResponseEntity.ok(userService.getAllDonors());
    }
    @GetMapping("/search")
    public ResponseEntity<Page<User>> searchUsers(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) User.Role role,
            @RequestParam(required = false) String telephone,
            @RequestParam(required = false) Boolean active,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> users = userService.getFilteredUsers(nom, email,username, role, telephone, active, pageable);
        return ResponseEntity.ok(users);
    }
    @GetMapping("/profile")
    public ResponseEntity<User> getAuthenticatedUserProfile(HttpServletRequest request) {
        Optional<User> user = userService.getAuthenticatedUser(request);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }



}