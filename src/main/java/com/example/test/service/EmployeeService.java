package com.example.test.service;


import com.example.test.entity.Employee;
import com.example.test.exception.ValidatorException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {


    List<Employee> getAll();

    Employee getEmployeeById(int id);

    Employee addEmployee(Employee employee) throws ValidatorException;

    Employee update(int id, Employee employee);

    void delete(int id);
}
