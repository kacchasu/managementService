package com.example.managementsystem.controllers;

import com.example.managementsystem.models.Employee;
import com.example.managementsystem.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/info")
    public String employeeInfo(Model model, Principal principal) {
        Employee employee = employeeService.findByUsername(principal.getName());
        model.addAttribute("employee", employee);
        return "employee/info"; // Thymeleaf шаблон для информации о работнике
    }

    @PostMapping("/leave")
    public String leaveJob(@RequestParam Long employeeId) {
        // Увольнение сотрудника
        employeeService.deleteById(employeeId);
        return "redirect:/login"; // После увольнения перенаправить на выход из аккаунта
    }
}
