package com.example.test.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "notifications")
@SequenceGenerator(name = "notificationIdGenerator", sequenceName = "notifications_id_seq",allocationSize = 1)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeeIdGenerator")
    private Integer id;
    private Integer receiver;
    private String message;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "send_date")
    private LocalDateTime sendDate;

    @ManyToOne
    @JoinColumn(name = "receiver", insertable = false, updatable = false)
    private Employee employee;

    @PrePersist
    public void prePersist() {
        creationDate = LocalDateTime.now();
    }

}
