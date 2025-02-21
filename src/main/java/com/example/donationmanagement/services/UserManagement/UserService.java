package com.example.donationmanagement.services.UserManagement;

import com.example.donationmanagement.entities.UserManagement.User;
import com.example.donationmanagement.repositories.UserManagement.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    // ✅ 1️⃣ Implémente `save(T entity)`
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    // ✅ 2️⃣ Implémente `registerUser`
    @Override
    public User registerUser(User user, MultipartFile photo, MultipartFile cin, MultipartFile justificatifDomicile,
                             MultipartFile rib, MultipartFile bulletinSalaire, MultipartFile declarationSante,
                             MultipartFile designationBeneficiaire, MultipartFile photoProfil) throws IOException {
        // Vérifier si l'email ou le username existent déjà
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé !");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username déjà pris !");
        }
        if (userRepository.findByTelephone(user.getTelephone()).isPresent()) {
            throw new RuntimeException("Numéro de téléphone déjà pris !");
        }

        // Crypter le mot de passe avant d'enregistrer l'utilisateur
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);// ⚠️ Mettre `false` car l'utilisateur doit vérifier son compte

        // ✅ Générer un token de vérification
        String verificationToken = UUID.randomUUID().toString();
        user.setVerificationToken(verificationToken);

        // 📌 Enregistrement des fichiers
        if (photo != null && !photo.isEmpty()) user.setPhotoProfil(photo.getBytes());
        if (cin != null && !cin.isEmpty()) user.setCin(cin.getBytes());
        if (justificatifDomicile != null && !justificatifDomicile.isEmpty()) user.setJustificatifDomicile(justificatifDomicile.getBytes());
        if (rib != null && !rib.isEmpty()) user.setRib(rib.getBytes());
        if (bulletinSalaire != null && !bulletinSalaire.isEmpty()) user.setBulletinSalaire(bulletinSalaire.getBytes());
        if (declarationSante != null && !declarationSante.isEmpty()) user.setDeclarationSante(declarationSante.getBytes());
        if (designationBeneficiaire != null && !designationBeneficiaire.isEmpty()) user.setDesignationBeneficiaire(designationBeneficiaire.getBytes());
        if (photoProfil != null && !photoProfil.isEmpty()) user.setPhotoProfil(photoProfil.getBytes());

        userRepository.save(user);

        // ✅ Envoyer l'email de vérification
        try {
            emailService.sendVerificationEmail(user.getEmail(), verificationToken);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email.");
        }

        return user;
    }

    // ✅ 2️⃣ Vérification du compte après clic sur le lien dans l'email
    @Override
    public String verifyUser(String token) {
        Optional<User> userOptional = userRepository.findByVerificationToken(token);
        if (!userOptional.isPresent()) {
            return "Token invalide ou expiré !";
        }

        User user = userOptional.get();
        user.setActive(true);  // ✅ Activer le compte
        user.setVerificationToken(null); // ✅ Supprimer le token après activation
        userRepository.save(user);

        return "Compte activé avec succès !";
    }

    // ✅ 3️⃣ Login
    @Override
    public Optional<User> login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.filter(value -> passwordEncoder.matches(password, value.getPassword()));
    }

    // ✅ 4️⃣ CRUD hérité de `IGenericService`
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User update(Long id, User userDetails, MultipartFile photo, MultipartFile cin,
                       MultipartFile justificatifDomicile, MultipartFile rib, MultipartFile bulletinSalaire,
                       MultipartFile declarationSante, MultipartFile designationBeneficiaire, MultipartFile photoProfil) {
        return userRepository.findById(id).map(user -> {
            user.setNom(userDetails.getNom());
            user.setPrenom(userDetails.getPrenom());
            user.setEmail(userDetails.getEmail());
            user.setUsername(userDetails.getUsername());
            user.setTelephone(userDetails.getTelephone());

            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            }

            user.setDateNaissance(userDetails.getDateNaissance());
            user.setActive(userDetails.isActive());
            user.setRole(userDetails.getRole());

            // ✅ Mise à jour des fichiers uniquement s'ils sont envoyés
            try {
                if (photo != null && !photo.isEmpty()) user.setPhotoProfil(photo.getBytes());
                if (cin != null && !cin.isEmpty()) user.setCin(cin.getBytes());
                if (justificatifDomicile != null && !justificatifDomicile.isEmpty()) user.setJustificatifDomicile(justificatifDomicile.getBytes());
                if (rib != null && !rib.isEmpty()) user.setRib(rib.getBytes());
                if (bulletinSalaire != null && !bulletinSalaire.isEmpty()) user.setBulletinSalaire(bulletinSalaire.getBytes());
                if (declarationSante != null && !declarationSante.isEmpty()) user.setDeclarationSante(declarationSante.getBytes());
                if (designationBeneficiaire != null && !designationBeneficiaire.isEmpty()) user.setDesignationBeneficiaire(designationBeneficiaire.getBytes());
                if (photoProfil != null && !photoProfil.isEmpty()) user.setPhotoProfil(photoProfil.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la mise à jour des fichiers.");
            }

            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
    public ResponseEntity<String> forgotPassword(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun utilisateur trouvé avec cet email.");
        }

        // ✅ Générer un token unique (UUID)
        String resetToken = UUID.randomUUID().toString();

        // ✅ Sauvegarder le token dans la base (optionnel, si tu as un champ `resetToken`)
        user.get().setResetToken(resetToken);
        userRepository.save(user.get());

        // ✅ Construire le lien de réinitialisation
        String resetLink = "http://localhost:9090/api/auth/reset-password?token=" + resetToken;

        // ✅ Envoyer l'email avec le lien
        try {
            emailService.sendPasswordResetEmail(user.get().getEmail(), resetLink);
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur d'envoi de l'email.");
        }

        return ResponseEntity.ok("Un email de réinitialisation a été envoyé.");
    }
    public ResponseEntity<String> resetPassword(String token, String newPassword) {
        Optional<User> user = userRepository.findByResetToken(token);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token invalide ou expiré.");
        }

        // ✅ Mettre à jour le mot de passe
        user.get().setPassword(passwordEncoder.encode(newPassword));

        // ✅ Supprimer le token après utilisation
        user.get().setResetToken(null);
        userRepository.save(user.get());

        return ResponseEntity.ok("Mot de passe réinitialisé avec succès !");
    }
}
