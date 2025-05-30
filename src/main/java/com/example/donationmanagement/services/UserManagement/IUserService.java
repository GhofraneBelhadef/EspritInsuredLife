package com.example.donationmanagement.services.UserManagement;

import com.example.donationmanagement.entities.UserManagement.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService extends IGenericService<User, Long> {
    User registerUser(User user,  MultipartFile cin, MultipartFile justificatifDomicile, MultipartFile rib,
                      MultipartFile bulletinSalaire, MultipartFile declarationSante, MultipartFile designationBeneficiaire, MultipartFile photoProfil) throws IOException;

    Optional<String> login(String email, String password);
    String verifyUser(String token);
    // ✅ Demande de réinitialisation de mot de passe
    ResponseEntity<String> forgotPassword(String email);

    // ✅ Réinitialisation du mot de passe avec token
    ResponseEntity<String> resetPassword(String token, String newPassword);
    Optional<User> getAuthenticatedUser(HttpServletRequest request);
    Page<User> getFilteredUsers(String nom, String email,String username, User.Role role, String telephone, Boolean active, Pageable pageable);

}
