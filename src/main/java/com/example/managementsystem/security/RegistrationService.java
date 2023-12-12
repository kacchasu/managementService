package com.example.managementsystem.security;

import com.example.managementsystem.models.Employee;
import com.example.managementsystem.models.Manager;
import com.example.managementsystem.models.UserDto;
import com.example.managementsystem.repositories.EmployeeRepository;
import com.example.managementsystem.repositories.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final EmployeeRepository employeeRepository;
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());

        if ("MANAGER".equals(userDto.getRole())) {
            Manager manager = new Manager(userDto.getUsername(), encodedPassword);
            managerRepository.save(manager);
        } else {
            Employee employee = new Employee(userDto.getUsername(), encodedPassword);
            employeeRepository.save(employee);
        }
    }
}
