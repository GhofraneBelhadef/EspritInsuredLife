package com.example.donationmanagement.services.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.Reward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IRewardService {
    Reward add(Reward reward);

    Reward update(Reward reward);

    void remove(Long reward_id);

    Reward getById(Long reward_id);

    Page<Reward> getAll(Pageable pageable);

    Page<Reward> getRewardsByType(String reward_type, Pageable pageable);
}