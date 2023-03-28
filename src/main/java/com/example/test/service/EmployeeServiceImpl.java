package com.example.test.service;


import com.example.test.entity.Employee;
import com.example.test.exception.NotFoundException;
import com.example.test.exception.ValidatorException;
import com.example.test.repository.EmployeeRepository;
import com.example.test.validator.PhoneNumberValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final ValidatorService validatorService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ValidatorService validatorService) {
        this.employeeRepository = employeeRepository;
        this.validatorService = validatorService;
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id).orElseThrow(() -> new NotFoundException("Employee not found"));
    }

    @Override
    public Employee addEmployee(Employee employee) throws ValidatorException {
        employee.setId(null);
        PhoneNumberValidator phoneValidator = new PhoneNumberValidator(employee.getSmsNumber());
        validatorService.validate(phoneValidator);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(int id, Employee employee) {
        var foundEmployee = getEmployeeById(id);
        foundEmployee.setFullName(employee.getFullName());
        foundEmployee.setSmsNumber(employee.getSmsNumber());
        foundEmployee.setDepartmentId(employee.getDepartmentId());
        return employeeRepository.save(foundEmployee);
    }

    @Override
    public void delete(int id) {
        var foundEmployee = getEmployeeById(id);
        employeeRepository.delete(foundEmployee);
    }
}
