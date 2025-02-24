package com.example.donationmanagement.repositories.UserManagement;

import com.example.donationmanagement.entities.DonationManagement.Campaign;
import com.example.donationmanagement.entities.UserManagement.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
