package com.example.test.service;

import com.example.test.entity.Notification;
import com.example.test.exception.NotFoundException;
import com.example.test.repository.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification addNotification(Notification notification) {
        notification.setId(null);
        return notificationRepository.save(notification);
    }

    @Override
    public Notification getNotificationById(int id) {
        return notificationRepository.findById(id).orElseThrow(() -> new NotFoundException("Notification not found"));
    }

    //არგაგზავნილი სმსების მეთოდი რეპოზიტორიშიც მაქვს და აქაც
    @Override
    public List<Notification> getNotSentNotifications(List<Notification> notificationList) {
        return notificationList.stream()
                .filter(n -> n.getSendDate() == null)
                .collect(Collectors.toList());
    }
    //გაგზავნილი სმსების მეთოდი რეპოზიტორიშიც მაქვს და აქაც
    @Override
    public List<Notification> getSentNotifications(List<Notification> notificationList) {
        return  notificationList.stream()
                .filter(n -> n.getSendDate() != null)
                .collect(Collectors.toList());

    }
    @Override
    public ResponseEntity<Notification> send(Page<Notification> notifications) {
        LocalDateTime now = LocalDateTime.now();
        for (Notification notifications1 : notifications){
            notifications1.setSendDate(now);
            notificationRepository.save(notifications1);
        }
        return null;
    }
    @Scheduled(fixedRate = 60 *1000)
    public void getLastSmsAndSend(){
        Pageable pageable = PageRequest.of(0,3);
        var lastUnsentNotifications = notificationRepository.findBySendDateIsNullOrderBySendDateAsc(pageable);
        send(lastUnsentNotifications);
        log.debug("Sms sent: " + lastUnsentNotifications.getNumberOfElements());
    }
}
