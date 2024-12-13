package com.example.register;

import com.example.register.employee.Employee;
import com.example.register.employee.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void shouldFindEmployeeByEmail() {
        // given
        Employee employee = new Employee("Mimmi", "Sandström", "mimmi@example.com", LocalDate.of(1994, 2, 12));
        employeeRepository.save(employee);

        // when
        Optional<Employee> foundEmployee = employeeRepository.findByUserEmail("mimmi@example.com");

        // then
        assertThat(foundEmployee).isPresent();
        assertThat(foundEmployee.get().getEmail()).isEqualTo("mimmi@example.com");
    }

    @Test
    void shouldNotFindEmployeeByEmail() {
        // when
        Optional<Employee> foundEmployee = employeeRepository.findByUserEmail("nonexistent@example.com");

        // then
        assertThat(foundEmployee).isNotPresent();
    }
}