package com.example.register.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// This is the repository class
// It is responsible for handling database operations
// It extends JpaRepository which is a JPA specific extension of Repository
@Repository
public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {
    // A custom query to find an employee by email
    @Query("SELECT e FROM Employee e WHERE e.email= ?1")
    Optional<Employee> findByUserEmail(String email);
}
