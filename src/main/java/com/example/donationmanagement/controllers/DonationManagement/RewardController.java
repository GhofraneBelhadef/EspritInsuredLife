package com.example.donationmanagement.controllers.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.Reward;
import com.example.donationmanagement.services.DonationManagement.IRewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @DeleteMapping("/remove/{reward_id}")
    public void removeReward(@PathVariable Long reward_id) {
        rewardService.remove(reward_id);
    }

    @GetMapping("/get/{reward_id}")
    public Reward getRewardById(@PathVariable Long reward_id) {
        return rewardService.getById(reward_id);
    }
    @GetMapping("/all")
    public Page<Reward> getAll(Pageable pageable) {
        return rewardService.getAll(pageable);
    }
    @GetMapping("/type/{reward_type}")
    public Page<Reward> getRewardsByType(@PathVariable String reward_type, Pageable pageable) {
        return rewardService.getRewardsByType(reward_type, pageable);
    }
}
