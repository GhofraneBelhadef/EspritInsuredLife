package com.example.donationmanagement.controllers.UserManagement;

import com.example.donationmanagement.entities.UserManagement.User;
import com.example.donationmanagement.services.UserManagement.EmailService;
import com.example.donationmanagement.services.UserManagement.IUserService;
import com.example.donationmanagement.services.UserManagement.QRCodeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Permet de tester avec Angular
public class AuthController {

    private final IUserService userService;
    private final ObjectMapper objectMapper;
    private final QRCodeService qrCodeService;
    private final Map<String, String> tokenStorage = new HashMap<>();


    @Autowired
    private EmailService emailService;

    public AuthController(IUserService userService, ObjectMapper objectMapper, QRCodeService qrCodeService) {
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.qrCodeService = qrCodeService;

    }

    // ✅ 1️⃣ Register User avec fichiers
    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public User registerUser(
            @RequestPart("user") String userJson, // 🔥 Doit être en `String`




            @RequestPart(value = "cin", required = false) MultipartFile cin,
            @RequestPart(value = "justificatifDomicile", required = false) MultipartFile justificatifDomicile,
            @RequestPart(value = "rib", required = false) MultipartFile rib,
            @RequestPart(value = "bulletinSalaire", required = false) MultipartFile bulletinSalaire,
            @RequestPart(value = "declarationSante", required = false) MultipartFile declarationSante,
            @RequestPart(value = "designationBeneficiaire", required = false) MultipartFile designationBeneficiaire,
            @RequestPart(value = "photoProfil", required = false) MultipartFile photoProfil
    ) throws IOException {

        // Convertir `userJson` (String) en Objet `User`
        User user = objectMapper.readValue(userJson, User.class);

        return userService.registerUser(user, photoProfil, cin, justificatifDomicile, rib, bulletinSalaire, declarationSante, designationBeneficiaire,photoProfil);
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
    @GetMapping("/success")
    public Map<String, Object> getUserInfo(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttributes(); // Retourne les infos de l'utilisateur
    }


}
