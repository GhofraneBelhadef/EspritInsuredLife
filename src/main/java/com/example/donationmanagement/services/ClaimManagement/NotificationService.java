package com.example.donationmanagement.services.ClaimManagement;

import com.example.donationmanagement.entities.ClaimManagement.Notification;
import com.example.donationmanagement.repositories.ClaimManagement.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;


    public void sendNotification(Notification notification) {
        if (notification.getComplaint() == null || notification.getComplaint().getId() == null) {
            throw new IllegalArgumentException("Notification must be linked to a valid Complaint.");
        }
        notification.setRead(false);
        notificationRepository.save(notification);
    }


    public List<Notification> getNotificationsForUser(Long userId) {
        return notificationRepository.findByUserId(userId);
    }


    public void markNotificationAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + id));
        notification.setRead(true);
        notificationRepository.save(notification);
    }


    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new RuntimeException("Notification not found with ID: " + id);
        }
        notificationRepository.deleteById(id);
    }


    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Notification not found with ID: " + id));
    }
}

