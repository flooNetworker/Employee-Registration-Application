package com.example.register.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;

// This is the controller class
// It is the entry point for the API
// It is responsible for handling HTTP requests
// API can be reached at http://localhost:8080/api/v1/employees
@RestController
@RequestMapping(path = "api/v1/employees")
public class EmployeeRegistrationController {

    private final EmployeeRegistrationService employeeRegistrationService;

    @Autowired
    public EmployeeRegistrationController(EmployeeRegistrationService employeeRegistrationService) {
        this.employeeRegistrationService = employeeRegistrationService;
    }

    @PostMapping
    public void registerNewEmployee(@RequestBody Employee employee) {
        employeeRegistrationService.registerNewEmployee(employee);
    }

    @GetMapping
    public List<String> getEmployees() {
        return employeeRegistrationService.getEmployees();
    }

    @GetMapping(path = "{employeeId}")
    public String getEmployee(@PathVariable("employeeId") Long employeeId) {
        return employeeRegistrationService.getEmployee(employeeId);
    }

    @DeleteMapping(path = "{employeeId}")
    public void deleteEmployee(@PathVariable("employeeId") Long employeeId) {
        employeeRegistrationService.deleteEmployee(employeeId);
    }

    @PutMapping(path = "{employeeId}")
    public void updateEmployee(@PathVariable("employeeId") Long employeeId,
                               @RequestParam(required = false) String firstName,
                               @RequestParam(required = false) String lastName,
                               @RequestParam(required = false) String email,
                               @RequestParam(required = false) LocalDate dob) {
        employeeRegistrationService.updateEmployee(employeeId, firstName, lastName, email, dob);
    }
}