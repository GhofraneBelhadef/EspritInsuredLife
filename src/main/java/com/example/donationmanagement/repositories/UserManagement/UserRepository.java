package com.example.donationmanagement.repositories.UserManagement;

import com.example.donationmanagement.entities.UserManagement.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByVerificationToken(String verificationToken);
    Optional<User> findByResetToken(String resetToken);
    Optional<User> findByTelephone(String telephone);
    Optional<User> findByGoogleId(String googleId);
    List<User> findByRole(User.Role role);

}
