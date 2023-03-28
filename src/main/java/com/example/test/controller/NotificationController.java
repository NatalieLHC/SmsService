package com.example.test.controller;

import com.example.test.entity.Employee;
import com.example.test.entity.Notification;
import com.example.test.repository.EmployeeRepository;
import com.example.test.repository.NotificationRepository;
import com.example.test.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;
    private final EmployeeRepository employeeRepository;

    public NotificationController(NotificationService notificationService, NotificationRepository notificationRepository, EmployeeRepository employeeRepository) {
        this.notificationService = notificationService;
        this.notificationRepository = notificationRepository;
        this.employeeRepository = employeeRepository;
    }

    @Operation(description = "შეტყობინების ძებნა id-ით")
    @GetMapping("/{id}")
    public Notification getNotificationById(@PathVariable int id) {
        return notificationService.getNotificationById(id);
    }

//    @GetMapping("/notsentsms")
//    public ResponseEntity<List<Notification>> getNotSentMessages() {
//        List<Notification> notifications = notificationRepository.findAll();
//        var filteredNotifications= notificationService.getNotSentNotifications(notifications);
//        return ResponseEntity.ok(filteredNotifications);
//    }

    @Operation(description = "ჯერ არ გაგზავნილი ყველაზე ძველი 50 შეტყობინების ძებნა (რაოდენობა კონფიგურირებადია)")
    @GetMapping("/notsentsms")
    public ResponseEntity<Page<Notification>> getNotSentMessages() {
        Pageable pageable = PageRequest.of(0,3);
        Page<Notification> notifications = notificationRepository.findBySendDateIsNullOrderBySendDateAsc(pageable);
        return ResponseEntity.ok(notifications);
    }

    @Operation(description = "გაგზავნილი შეტყობინების ძებნა")
    @GetMapping("/sentsms")
    public ResponseEntity<List<Notification>> getSentMessages() {
        List<Notification> notifications = notificationRepository.findBySendDateIsNotNull();
        return ResponseEntity.ok(notifications);
    }


    //    @GetMapping("/sentsms")
//    public ResponseEntity<List<Notification>> getSentNotifications(){
//        List<Notification> notifications = notificationRepository.findAll();
//        var filteredNotifications = notificationService.getSentNotifications(notifications);
//        return ResponseEntity.ok(filteredNotifications);
//    }
    @Operation(description = "შეტყობინების შექმნა")
    @PostMapping("/registersms")
    public ResponseEntity<Notification> add(@RequestBody Notification notification) {
        notificationService.addNotification(notification);
        var location = UriComponentsBuilder.fromPath("/registersms/" + notification.getId()).build().toUri();
        return ResponseEntity.created(location).body(notification);
    }

    @Operation(description = "ყველაზე ძველი შექმნილი 50 შეტყობინების ფორსირებულად გაგზავნა")
    @PutMapping("/send")
    public ResponseEntity<String> sendSms() {

        notificationService.getLastSmsAndSend();
        return ResponseEntity.ok("ok");

    }

}
