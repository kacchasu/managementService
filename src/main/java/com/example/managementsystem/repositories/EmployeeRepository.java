package com.example.managementsystem.repositories;

import com.example.managementsystem.models.Employee;
import com.example.managementsystem.models.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByUsername(String username);

    List<Employee> findByManagerIsNull();

    List<Employee> findByManager(Manager manager);
}
