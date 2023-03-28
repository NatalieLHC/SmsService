package com.example.test.service;

import com.example.test.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {

    Notification addNotification(Notification notification);

    Notification getNotificationById(int id);

    List<Notification> getNotSentNotifications(List<Notification> notificationList);

    List<Notification> getSentNotifications(List<Notification> notificationList);

    public ResponseEntity<Notification> send(Page<Notification> notifications);

    public void getLastSmsAndSend();
}
