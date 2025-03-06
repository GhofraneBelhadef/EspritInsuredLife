package com.example.donationmanagement.services.DonationManagement;

import com.example.donationmanagement.entities.DonationManagement.Campaign;
import com.example.donationmanagement.repositories.DonationManagement.CampaignRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class SocialMediaService {
    private static final Logger log = LoggerFactory.getLogger(SocialMediaService.class);

    private final WebClient webClient;
    @Autowired
    private CampaignRepository campaignRepository;

    public SocialMediaService() {
        this.webClient = WebClient.create("http://localhost:5000"); // URL de l'API Flask
    }

    // Méthode pour générer du contenu avec l'API Flask
    public String generateContent(Long campaign_id) {
        return webClient.post()
                .uri("/generate-content")
                .bodyValue(new CampaignRequest(campaign_id)) // Envoyer l'ID de la campagne
                .retrieve()
                .bodyToMono(ContentResponse.class) // Réceptionner la réponse
                .block()
                .getContent();
    }
    public void scheduleSocialMediaPost(Long campaign_id, Date postDate) {
        log.info("Scheduling post for campaign ID: {}", campaign_id);
        log.info("Post date: {}", postDate);

        Campaign campaign = campaignRepository.findById(campaign_id)
                .orElseThrow(() -> {
                    log.error("Campaign not found with ID: {}", campaign_id);
                    return new RuntimeException("Campaign not found with ID: " + campaign_id);
                });

        // Update the lastSocialMediaPostDate field
        campaign.setLastSocialMediaPostDate(postDate);
        log.info("Updated lastSocialMediaPostDate: {}", campaign.getLastSocialMediaPostDate());

        // Save the updated campaign
        campaignRepository.save(campaign);
    }
    public void analyzeSocialMediaPerformance(Long campaign_id, int likes, int shares, int comments) {
        // Récupérer la campagne depuis la base de données
        Campaign campaign = campaignRepository.findById(campaign_id)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        // Mettre à jour les métriques de performance
        campaign.setSocialMediaLikes(likes);
        campaign.setSocialMediaShares(shares);
        campaign.setSocialMediaComments(comments);

        // Sauvegarder la campagne mise à jour
        campaignRepository.save(campaign);
    }

    // Classe pour représenter la requête
    private static class CampaignRequest {
        private Long campaign_id;

        public CampaignRequest(Long campaign_id) {
            this.campaign_id = campaign_id;
        }

        public Long getCampaign_id() {
            return campaign_id;
        }

        public void setCampaign_id(Long campaign_id) {
            this.campaign_id = campaign_id;
        }
    }

    // Classe pour représenter la réponse
    private static class ContentResponse {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}