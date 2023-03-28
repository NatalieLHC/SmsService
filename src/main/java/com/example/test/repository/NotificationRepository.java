package com.example.test.repository;

import com.example.test.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    Page<Notification> findBySendDateIsNullOrderBySendDateAsc(Pageable pageable);
    List<Notification> findBySendDateIsNotNull();
}
