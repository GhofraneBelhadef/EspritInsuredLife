package com.example.donationmanagement.controllers.ClaimManagement;

import com.example.donationmanagement.entities.ClaimManagement.Notification;
import com.example.donationmanagement.services.ClaimManagement.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody Notification notification) {
        notificationService.sendNotification(notification);
        return ResponseEntity.status(HttpStatus.CREATED).body("Notification sent successfully.");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUser(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getNotificationsForUser(userId);
        return ResponseEntity.ok(notifications);
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<String> markNotificationAsRead(@PathVariable Long id) {
        notificationService.markNotificationAsRead(id);
        return ResponseEntity.ok("Notification marked as read.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Notification deleted successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Notification notification = notificationService.getNotificationById(id);
        return ResponseEntity.ok(notification);
    }
}
