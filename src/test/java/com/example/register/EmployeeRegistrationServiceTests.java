package com.example.register;

import com.example.register.employee.Employee;
import com.example.register.employee.EmployeeRegistrationService;
import com.example.register.employee.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class EmployeeRegistrationServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @Captor
    private ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);

    @InjectMocks
    private EmployeeRegistrationService employeeRegistrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void canRegisterNewEmployee() {
        // given, the employee object is created
        Employee employee = new Employee("Mimmi", "Sandström", "mimmi@gmail.com", LocalDate.of(1994, 2, 12));
        // when, the register employee method is called with this employee
        employeeRegistrationService.registerNewEmployee(employee);
        // then, verify the save method and that correct employee is called, use the captor
        verify(employeeRepository).save(employeeArgumentCaptor.capture());
        Employee capturedEmployee = employeeArgumentCaptor.getValue();
        assertThat(capturedEmployee).isEqualTo(employee);
    }
    @Test
    void doNotSaveEmployeeWithSameEmail(){
        // given
        Employee employee = new Employee("Mimmi", "Sandström", "mimmi@gmail.com", LocalDate.of(1994, 2, 12));
        given(employeeRepository.findByUserEmail(employee.getEmail())).willReturn(Optional.of(employee));
        // when
        assertThatThrownBy(() -> employeeRegistrationService.registerNewEmployee(employee))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Cannot add new employee, the email address is already taken");
        // then
        verify(employeeRepository, never()).save(any(Employee.class));
    }
    @Test
    void canGetEmployees() {
        // when
        employeeRegistrationService.getEmployees();
        // then
        verify(employeeRepository).findAll();
    }

    @Test
    void canGetEmployee() {
        // given
        Long employeeId = 1L;
        Employee employee = new Employee("Mimmi", "Sandström", "mimmi@gmail.com", LocalDate.of(1994, 2, 12));
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee)); // mock the repository
        // when
        String result = employeeRegistrationService.getEmployee(employeeId);
        // then
        assertThat(result).isEqualTo(employee.toString());
    }

    @Test
    void canGetBirthdayEmployee() {
        // given
        Long employeeId = 1L;
        Employee employee = new Employee("Mimmi", "Sandström", "mimmi@gmail.com", LocalDate.of(1994, LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()));
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee)); // mock the repository
        // when
        String result = employeeRegistrationService.getEmployee(employeeId);
        // then
        assertThat(result).isEqualTo("Happy Birthday " + employee.toString());
    }

    @Test
    void canDeleteEmployee() {
        // given
        Long employeeId = 1L;
        Employee employee = new Employee("Mimmi", "Sandström", "mimmi@example.com", LocalDate.of(1994, 2, 12));
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.existsById(employeeId)).thenReturn(true);
        // when
        employeeRegistrationService.deleteEmployee(employeeId);
        // then
        verify(employeeRepository).deleteById(employeeId);
    }

    @Test
    void canNotDeleteEmployee() {
        // given
        Long employeeId = 1L;
        when(employeeRepository.existsById(employeeId)).thenReturn(true);
        // when
        assertThatThrownBy(() -> employeeRegistrationService.deleteEmployee(employeeId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Employee with id " + employeeId + " does not exist");
        // then
        verify(employeeRepository, never()).deleteById(employeeId);
    }
    @Test
    void canUpdateEmployee() {
        // given
        Long employeeId = 1L;
        Employee employee = new Employee("Mimmi", "Sandström", "mimmi@example.com", LocalDate.of(1994, 2, 12));
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        String newFirstName = "Emelie";
        String newEmail = "emelie@example.com";
        // when
        employeeRegistrationService.updateEmployee(employeeId, newFirstName, null, newEmail, null);
        // then
        verify(employeeRepository).save(employeeArgumentCaptor.capture());
        Employee capturedEmployee = employeeArgumentCaptor.getValue();
        assertThat(capturedEmployee.getFirstName()).isEqualTo(newFirstName);
        assertThat(capturedEmployee.getEmail()).isEqualTo(newEmail);
        }
}