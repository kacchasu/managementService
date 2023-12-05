package com.example.managementsystem.security;

import com.example.managementsystem.models.Employee;
import com.example.managementsystem.models.Manager;
import com.example.managementsystem.repositories.EmployeeRepository;
import com.example.managementsystem.repositories.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final ManagerRepository managerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username);
        if (employee != null) {
            return new User(employee.getUsername(), employee.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_EMPLOYEE")));
        }

        Manager manager = managerRepository.findByUsername(username);
        if (manager != null) {
            return new User(manager.getUsername(), manager.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_MANAGER")));
        }

        throw new UsernameNotFoundException("User not found");
    }
}
