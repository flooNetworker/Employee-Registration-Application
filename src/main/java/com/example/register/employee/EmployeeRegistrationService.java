package com.example.register.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

// This is the service class
// This connect the API layer with the DAO layer (data access layer)
// This class is responsible for handling the business logic
// To find the h2 database, go to http://localhost:8080/h2-console
@Service
public class EmployeeRegistrationService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeRegistrationService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Register a new employee
    public void registerNewEmployee(Employee employee) {
        isValidInput(employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getDob());
        employeeRepository.save(employee);
    }
    // Get all employees
    public List<String> getEmployees() {
        return employeeRepository.findAll().stream()
                .map(Employee::toString)
                .collect(Collectors.toList());
    }

    // Get one specific employee
    public String getEmployee(Long employeeId) {
        LocalDate today = LocalDate.now();
        Employee employee = findEmployeeById(employeeId);
        if (today.getMonth() == employee.getDob().getMonth() && today.getDayOfMonth() == employee.getDob().getDayOfMonth()) {
            // Extra feature if it's the employee's birthday
            return "Happy Birthday " + employee.toString();

        }
        return  employee.toString();
    }

    // Delete existing employee
    public void deleteEmployee(Long employeeId) {
        findEmployeeById(employeeId);
        employeeRepository.deleteById(employeeId);
    }

    // Update existing employee
    @Transactional
    public void updateEmployee(Long employeeId, String firstName, String lastName, String email, LocalDate dob) {
        Employee employee = findEmployeeById(employeeId);
        // set fields
        if (isValid(firstName)) {
            employee.setFirstName(firstName);
        }
        if (isValid(lastName)) {
            employee.setLastName(lastName);
        }
        if (isValid(email)) {
            employee.setEmail(email);
        }
        if (isValid(dob)) {
            employee.setDob(dob);
        }
        employeeRepository.save(employee);
    }

    private Employee findEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new IllegalStateException("Employee with id " + employeeId + " does not exist"));
    }

    private boolean isValid(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        if (value.contains("@") && employeeRepository.findByUserEmail(value).isPresent()) {
            throw new IllegalStateException("Cannot add new employee, the email address is already taken");
        }
        return true;
    }

    private boolean isValid(LocalDate value) {
        return value != null;
    }

    private void isValidInput(String firstName, String lastName, String email, LocalDate dob) {
        for (String value : new String[]{firstName, lastName, email}) {
            if (!isValid(value)) {
                throw new IllegalArgumentException("One or more input fields are invalid or missing");
            }
        }
        if (!isValid(dob)) {
            throw new IllegalArgumentException("Date of birth is invalid or missing");
        }
    }
}
