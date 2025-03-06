package com.example.donationmanagement.services.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.Reward;
import com.example.donationmanagement.repositories.DonationManagement.RewardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RewardService implements IRewardService {

    @Autowired
    private RewardRepository rewardRepo;

    @Override
    public Reward add(Reward reward) {
        log.info("Adding reward: {}", reward);
        return rewardRepo.save(reward);
    }

    @Override
    public Reward update(Reward reward) {
        log.info("Updating reward: {}", reward);
        return rewardRepo.save(reward);
    }

    @Override
    public void remove(Long reward_id) {
        log.info("Removing reward with ID: {}", reward_id);
        rewardRepo.deleteById(reward_id);
    }

    @Override
    public Reward getById(Long reward_id) {
        log.info("Fetching reward with ID: {}", reward_id);
        return rewardRepo.findById(reward_id).orElse(null);
    }


    public Page<Reward> getAll(Pageable pageable) {
        return rewardRepo.findAll(pageable);
    }

    public Page<Reward> getRewardsByType(String reward_type, Pageable pageable) {
        return rewardRepo.findByRewardType(reward_type, pageable);
    }
}
