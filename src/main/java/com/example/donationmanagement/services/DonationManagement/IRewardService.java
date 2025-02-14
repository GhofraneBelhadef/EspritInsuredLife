package com.example.donationmanagement.services.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.Reward;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IRewardService {
    Reward add(Reward reward);
    Reward update(Reward reward);
    void remove(Long id);
    Reward getById(Long id);
    List<Reward> getAll();
}
