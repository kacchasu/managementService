package com.example.managementsystem.services;

import com.example.managementsystem.models.Employee;
import com.example.managementsystem.models.Manager;
import com.example.managementsystem.models.Rating;
import com.example.managementsystem.repositories.EmployeeRepository;
import com.example.managementsystem.repositories.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final RatingRepository ratingRepository;

    public Employee findByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    public void deleteById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        ratingRepository.deleteAll(ratingRepository.findByRatedByUser(employee));
        ratingRepository.deleteAll(ratingRepository.findByRatedUser(employee));

        employeeRepository.delete(employee);
    }

    public List<Employee> findFreeEmployees() {
        return employeeRepository.findByManagerIsNull();
    }

    public void assignManager(Long employeeId, Manager manager) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Сотрудник не найден"));
        employee.setManager(manager);
        employeeRepository.save(employee);
    }

    public void updateEmployeePosition(Long employeeId, String newPosition) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Сотрудник не найден"));
        employee.setPosition(newPosition);
        employeeRepository.save(employee);
    }

    public List<Employee> findEmployeesByManager(Manager manager) {
        return employeeRepository.findByManager(manager);
    }

    private final RatingService ratingService; // Сервис для работы с оценками

    public Optional<Employee> findById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public void updateAverageRating(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Сотрудник не найден"));
        List<Rating> ratings = ratingService.findByRatedUser(employee);

        if (!ratings.isEmpty()) {
            double totalScore = ratings.stream().mapToDouble(Rating::getScore).sum();
            double averageScore = totalScore / ratings.size();
            employee.setAverageRating(averageScore);
            employeeRepository.save(employee);
        }
    }
}
