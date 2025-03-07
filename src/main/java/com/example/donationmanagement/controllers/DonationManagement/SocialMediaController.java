package com.example.donationmanagement.controllers.DonationManagement;

import com.example.donationmanagement.services.DonationManagement.SocialMediaService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@RequestMapping("/api/social-media")
public class SocialMediaController {
    private static final Logger log = (Logger) LoggerFactory.getLogger(SocialMediaController.class);

    @Autowired
    private SocialMediaService socialMediaService;

    @PostMapping("/generate-content/{campaign_id}")
    public String generateContent(@PathVariable Long campaign_id) {
        return socialMediaService.generateContent(campaign_id);
    }

    @PostMapping("/schedule-post/{campaign_id}")
    public void schedulePost(
            @PathVariable Long campaign_id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date postDate) {
        log.info("Received request to schedule post for campaign ID: {}");
        log.info("Post date: {}");

        socialMediaService.scheduleSocialMediaPost(campaign_id, postDate);
    }

    @PostMapping("/analyze-performance/{campaign_id}")
    public void analyzePerformance(@PathVariable Long campaign_id, @RequestParam int likes, @RequestParam int shares, @RequestParam int comments) {
        socialMediaService.analyzeSocialMediaPerformance(campaign_id, likes, shares, comments);
    }
}