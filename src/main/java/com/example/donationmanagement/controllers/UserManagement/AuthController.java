package com.example.donationmanagement.controllers.UserManagement;

import com.example.donationmanagement.entities.UserManagement.User;
import com.example.donationmanagement.repositories.UserManagement.UserRepository;
import com.example.donationmanagement.services.UserManagement.EmailService;
import com.example.donationmanagement.services.UserManagement.IUserService;
import com.example.donationmanagement.services.UserManagement.JwtService;
import com.example.donationmanagement.services.UserManagement.QRCodeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Permet de tester avec Angular
@Validated
public class AuthController {

    private final IUserService userService;
    private final ObjectMapper objectMapper;
    private final QRCodeService qrCodeService;
    private final Map<String, String> tokenStorage = new HashMap<>();
    private final Validator validator;


    @Autowired
    private EmailService emailService;


    public AuthController(IUserService userService, ObjectMapper objectMapper, QRCodeService qrCodeService, Validator validator) {
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.qrCodeService = qrCodeService;
        this.validator = validator;

    }

    // ✅ 1️⃣ Register User avec fichiers
    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ResponseEntity<?> registerUser(
            @RequestPart("user") @Valid String userJson,
            @RequestPart(value = "cin", required = false) MultipartFile cin,
            @RequestPart(value = "justificatifDomicile", required = false) MultipartFile justificatifDomicile,
            @RequestPart(value = "rib", required = false) MultipartFile rib,
            @RequestPart(value = "bulletinSalaire", required = false) MultipartFile bulletinSalaire,
            @RequestPart(value = "declarationSante", required = false) MultipartFile declarationSante,
            @RequestPart(value = "designationBeneficiaire", required = false) MultipartFile designationBeneficiaire,
            @RequestPart(value = "photoProfil", required = false) MultipartFile photoProfil
    ) {
        try {
            // 🛠️ **Convertir `userJson` en Objet `User`**
            User user = objectMapper.readValue(userJson, User.class);
            user.setRole(User.Role.CLIENT);

            // 🛠️ **Vérifier les contraintes de validation**
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            if (!violations.isEmpty()) {
                List<String> errors = violations.stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
            }

            // ✅ **Appel correct de `registerUser` (éviter le doublon de `photoProfil`)**
            User savedUser = userService.registerUser(user, cin, justificatifDomicile, rib, bulletinSalaire, declarationSante, designationBeneficiaire, photoProfil);

            return ResponseEntity.ok().body(savedUser);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"Erreur dans le format JSON de l'utilisateur.\"}");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Erreur lors du traitement des fichiers : " + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Une erreur inattendue est survenue : " + e.getMessage() + "\"}");
        }
    }



    @GetMapping("/verify")
    public String verifyUser(@RequestParam("token") String token) {
        return userService.verifyUser(token);
    }

    // ✅ 2️⃣ Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        Optional<String> token = userService.login(email, password);

        if (token.isPresent()) {
            return ResponseEntity.ok().body(Map.of("token", token.get()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Échec de connexion : email ou mot de passe incorrect");
        }
    }
    @GetMapping("/test-email")
    public String testEmail() {
        try {
            emailService.sendVerificationEmail("aminehamrouni8@gmail.com", "123456");
            return "✅ Email envoyé avec succès !";
        } catch (Exception e) {
            return "❌ Erreur : " + e.getMessage();
        }
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        return userService.forgotPassword(email);
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        return userService.resetPassword(token, newPassword);
    }
    @GetMapping("/generate-qr/{username}")
    public ResponseEntity<String> generateQRCode(@PathVariable String username) {
        try {
            // 🔹 Générer un token unique
            String token = UUID.randomUUID().toString();
            // 🔹 Stocker le token temporaire (à remplacer par une base de données ou Redis en production)
            tokenStorage.put(token, username);
            // 🔹 Générer le QR Code en Base64
            String qrCode = qrCodeService.generateQRCode(token);
            return ResponseEntity.ok(qrCode);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur lors de la génération du QR Code");
        }
    }

    // ✅ Vérifier si un QR Code est valide
    @PostMapping("/validate-qr")
    public ResponseEntity<String> validateQRCode(@RequestParam String token) {
        if (tokenStorage.containsKey(token)) {
            String username = tokenStorage.get(token);
            tokenStorage.remove(token); // 🔹 Supprime le token après validation
            return ResponseEntity.ok("✅ Connexion réussie pour l'utilisateur : " + username);
        } else {
            return ResponseEntity.status(401).body("⛔ QR Code invalide ou expiré");
        }
    }
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;
    @GetMapping("/success")
    public ResponseEntity<?> getUserInfo(OAuth2AuthenticationToken authenticationToken) {
        if (authenticationToken == null || authenticationToken.getPrincipal() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Utilisateur non authentifié après OAuth2 !"));
        }

        OAuth2User oAuth2User = authenticationToken.getPrincipal();
        String googleId = oAuth2User.getAttribute("sub");
        String email = oAuth2User.getAttribute("email");

        Optional<User> existingUser = userRepository.findByGoogleId(googleId);

        if (existingUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Utilisateur non trouvé après OAuth2 !"));
        }

        User user = existingUser.get();

        // ✅ Générer le Token JWT sans le stocker
        String jwtToken = jwtService.generateToken(user);

        return ResponseEntity.ok(Map.of(
                "token", jwtToken,
                "user", oAuth2User.getAttributes()
        ));
    }



}
