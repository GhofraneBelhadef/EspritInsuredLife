package com.example.donationmanagement.repositories.UserManagement;

import com.example.donationmanagement.entities.UserManagement.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByVerificationToken(String verificationToken);
    Optional<User> findByResetToken(String resetToken);
    Optional<User> findByTelephone(String telephone);
    Optional<User> findByGoogleId(String googleId);
    Page<User> findByRole(User.Role role, Pageable pageable);
    Page<User> findAll(Pageable pageable);

}
