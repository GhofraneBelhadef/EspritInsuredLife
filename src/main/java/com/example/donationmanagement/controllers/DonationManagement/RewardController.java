package com.example.donationmanagement.controllers.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.Reward;
import com.example.donationmanagement.services.DonationManagement.IRewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rewards")
public class RewardController {

    @Autowired
    private IRewardService rewardService;

    @PostMapping("/add")
    public Reward addReward(@RequestBody Reward reward) {
        return rewardService.add(reward);
    }

    @PutMapping("/update")
    public Reward updateReward(@RequestBody Reward reward) {
        return rewardService.update(reward);
    }

    @DeleteMapping("/remove/{id}")
    public void removeReward(@PathVariable Long id) {
        rewardService.remove(id);
    }

    @GetMapping("/get/{id}")
    public Reward getRewardById(@PathVariable Long id) {
        return rewardService.getById(id);
    }

    @GetMapping("/all")
    public List<Reward> getAllRewards() {
        return rewardService.getAll();
    }
}
