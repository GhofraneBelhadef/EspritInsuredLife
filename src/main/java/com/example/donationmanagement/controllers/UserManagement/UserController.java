package com.example.donationmanagement.controllers.UserManagement;

import com.example.donationmanagement.entities.UserManagement.User;
import com.example.donationmanagement.repositories.UserManagement.UserRepository;
import com.example.donationmanagement.services.UserManagement.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
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
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(
            @PathVariable Long id,
            @RequestPart("user") String userJson,
            @RequestPart(value = "photo", required = false) MultipartFile photo,
            @RequestPart(value = "cin", required = false) MultipartFile cin,
            @RequestPart(value = "justificatifDomicile", required = false) MultipartFile justificatifDomicile,
            @RequestPart(value = "rib", required = false) MultipartFile rib,
            @RequestPart(value = "bulletinSalaire", required = false) MultipartFile bulletinSalaire,
            @RequestPart(value = "declarationSante", required = false) MultipartFile declarationSante,
            @RequestPart(value = "designationBeneficiaire", required = false) MultipartFile designationBeneficiaire,
            @RequestPart(value = "photoProfil", required = false) MultipartFile photoProfil
    ) throws IOException {
        User userDetails = objectMapper.readValue(userJson, User.class);
        return userService.update(id, userDetails, photo, cin, justificatifDomicile, rib, bulletinSalaire, declarationSante, designationBeneficiaire,photoProfil);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
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


}
