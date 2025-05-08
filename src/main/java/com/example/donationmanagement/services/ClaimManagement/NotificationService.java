package com.example.donationmanagement.services.ClaimManagement;

import com.example.donationmanagement.entities.ClaimManagement.Notification;
import com.example.donationmanagement.repositories.ClaimManagement.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public Notification sendNotification(Notification notification) {
        if (notification.getComplaint() == null || notification.getComplaint().getId() == null) {
            throw new IllegalArgumentException("Notification must be linked to a valid Complaint.");
        }
        notification.setRead(false);
        Notification savedNotification = notificationRepository.save(notification);
        // Broadcast the notification to all subscribed clients
        messagingTemplate.convertAndSend("/topic/notifications", savedNotification);
        return savedNotification;
    }

    public List<Notification> getNotificationsForUser(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public Notification markNotificationAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + id));
        notification.setRead(true);
        Notification updatedNotification = notificationRepository.save(notification);
        messagingTemplate.convertAndSend("/topic/notifications", updatedNotification); // Broadcast update
        return updatedNotification;
    }

    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new RuntimeException("Notification not found with ID: " + id);
        }
        notificationRepository.deleteById(id);
         // Broadcast deletion (optional)
    }

    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + id));
    }
}