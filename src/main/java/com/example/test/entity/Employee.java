package com.example.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "employees")
@SequenceGenerator(name = "employeeIdGenerator", sequenceName = "employees_id_seq",allocationSize = 1)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeeIdGenerator")
    private Integer id;
    @Column(name = "full_name")
    private String fullName;
    @Column(unique = true, name = "sms_number")
    private String smsNumber;
    @Column(name = "department_id")
    private Integer departmentId;

    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    private List<Notification> receivedMessages;

    @PreRemove
    private void preventDeleteIfMessagesReceived(){
        if (!receivedMessages.isEmpty()) {
            throw new IllegalStateException("Can't delete user with received messages");
        }
    }
}
