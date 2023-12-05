package com.example.managementsystem.services;

import com.example.managementsystem.models.Employee;
import com.example.managementsystem.models.Manager;
import com.example.managementsystem.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    public void deleteById(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    public List<Employee> findFreeEmployees() {
        return employeeRepository.findByManagerIsNull();
    }

    public void assignManager(Long employeeId, Manager manager) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Сотрудник не найден"));
        employee.setManager(manager);
        employeeRepository.save(employee);
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }
    public void updateEmployeePosition(Long employeeId, String newPosition) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Сотрудник не найден"));
        employee.setPosition(newPosition);
        employeeRepository.save(employee);
    }

    public List<Employee> findEmployeesByManager(Manager manager) {
        return employeeRepository.findByManager(manager);
    }
}
