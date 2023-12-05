package com.example.managementsystem.controllers;

import com.example.managementsystem.models.Employee;
import com.example.managementsystem.models.Manager;
import com.example.managementsystem.services.EmployeeService;
import com.example.managementsystem.services.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/managers")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;
    private final EmployeeService employeeService;

    @GetMapping
    public String managerDashboard(Model model, Principal principal) {
        Manager manager = managerService.findByUsername(principal.getName());
        model.addAttribute("manager", manager);
        List<Employee> myEmployees = employeeService.findEmployeesByManager(manager);
        model.addAttribute("myEmployees", myEmployees);
        return "manager/dashboard"; // Обновленный Thymeleaf шаблон для дашборда менеджера
    }

    @PostMapping("/employee/fire")
    public String fireEmployee(@RequestParam Long employeeId) {
        // Уволить работника
        employeeService.deleteById(employeeId);
        return "redirect:/managers";
    }

    @PostMapping("/employee/update")
    public String updateEmployee(@RequestParam Long employeeId, @RequestParam String newPosition) {
        // Обновить должность работника
        employeeService.updateEmployeePosition(employeeId, newPosition);
        return "redirect:/managers";
    }

    @GetMapping("/hire")
    public String showHirePage(Model model) {
        List<Employee> freeEmployees = employeeService.findFreeEmployees();
        model.addAttribute("freeEmployees", freeEmployees);
        return "manager/hire"; // Thymeleaf шаблон для найма работников
    }

    @PostMapping("/hire")
    public String hireEmployee(@RequestParam Long employeeId, Principal principal) {
        Manager manager = managerService.findByUsername(principal.getName());
        employeeService.assignManager(employeeId, manager);
        return "redirect:/managers";
    }
}
