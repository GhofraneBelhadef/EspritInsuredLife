package com.example.donationmanagement.services.UserManagement;

import com.example.donationmanagement.entities.UserManagement.User;
import com.example.donationmanagement.repositories.UserManagement.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        String googleId = oAuth2User.getAttribute("sub"); // Google ID
        String email = oAuth2User.getAttribute("email");
        String firstName = oAuth2User.getAttribute("given_name");
        String lastName = oAuth2User.getAttribute("family_name");

        Optional<User> existingUser = userRepository.findByGoogleId(googleId);

        User user;
        if (existingUser.isPresent()) {
            user = existingUser.get(); // 🔄 Utilisateur déjà existant
        } else {
            // 🆕 Créer un nouvel utilisateur avec profil incomplet
            user = new User();
            user.setGoogleId(googleId);
            user.setEmail(email);
            user.setNom(lastName);
            user.setPrenom(firstName);
            user.setActive(true); // ✅ Compte activé automatiquement
            user.setRole(User.Role.CLIENT); // Définir un rôle par défaut

            // ✅ Générer un mot de passe aléatoire temporaire
            user.setPassword(RandomStringUtils.randomAlphanumeric(20));

            // ✅ Laisser les fichiers et la photo de profil vides (à compléter plus tard)
            user.setCin(null);
            user.setJustificatifDomicile(null);
            user.setRib(null);
            user.setBulletinSalaire(null);
            user.setDeclarationSante(null);
            user.setDesignationBeneficiaire(null);
            user.setPhotoProfil(null);

            // ✅ Initialiser le téléphone à NULL (l'utilisateur devra le renseigner)
            user.setTelephone(null);
            if (user.getUsername() == null) {
                user.setUsername("google_user_" + UUID.randomUUID().toString().substring(0, 8)); // Générer un username temporaire
            }

            // ❌ Marquer le profil comme incomplet
            user.setProfilComplet(false);

            userRepository.save(user);
        }

        return oAuth2User;
    }
}
