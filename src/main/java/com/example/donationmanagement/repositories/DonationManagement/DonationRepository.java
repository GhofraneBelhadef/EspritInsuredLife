package com.example.donationmanagement.repositories.DonationManagement;
import com.example.donationmanagement.entities.DonationManagement.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository  extends JpaRepository<Donation, Long>{
}
