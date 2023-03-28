package com.example.test.controller;

import com.example.test.entity.Employee;
import com.example.test.entity.Notification;
import com.example.test.exception.ValidatorException;
import com.example.test.repository.EmployeeRepository;
import com.example.test.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    @Operation(description = "მომხმარებლის ძებნა id-ით")
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @Operation(description = "ყველა მომხმარებლის ძებნა")
    @GetMapping()
    public List<Employee> getAll() {
        return employeeService.getAll();
    }

    @Operation(description = "მომხმარებლის რეგისტრაცია")
    @PostMapping("/register")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) throws ValidatorException {
        employeeService.addEmployee(employee);
        var location = UriComponentsBuilder.fromPath("/employees/" + employee.getId()).build().toUri();
        return ResponseEntity.created(location).body(employee);
    }

    @Operation(description = "მომხმარებლის მონაცემების რედაქტირება")
    @PutMapping("/{id}")
    public Employee update(@RequestBody Employee employee, @PathVariable int id) {
        return employeeService.update(id,employee);
    }

    @Operation(description = "მომხმარებლის წაშლა")
    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> delete(@PathVariable int id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
