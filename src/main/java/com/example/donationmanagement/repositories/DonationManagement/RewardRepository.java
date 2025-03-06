package com.example.donationmanagement.repositories.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {
    @Query("SELECT r FROM Reward r WHERE r.reward_type = :rewardType")
    Page<Reward> findByRewardType(@Param("rewardType") String rewardType, Pageable pageable);
}
