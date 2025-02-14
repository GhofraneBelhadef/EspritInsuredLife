package com.example.donationmanagement.services.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.Reward;
import com.example.donationmanagement.repositories.DonationManagement.RewardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void remove(Long id) {
        log.info("Removing reward with ID: {}", id);
        rewardRepo.deleteById(id);
    }

    @Override
    public Reward getById(Long id) {
        log.info("Fetching reward with ID: {}", id);
        return rewardRepo.findById(id).orElse(null);
    }

    @Override
    public List<Reward> getAll() {
        log.info("Fetching all rewards");
        return rewardRepo.findAll();
    }
}
