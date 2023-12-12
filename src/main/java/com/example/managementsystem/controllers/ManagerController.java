package com.example.managementsystem.controllers;

import com.example.managementsystem.models.Employee;
import com.example.managementsystem.models.Manager;
import com.example.managementsystem.models.Rating;
import com.example.managementsystem.services.EmployeeService;
import com.example.managementsystem.services.ManagerService;
import com.example.managementsystem.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/managers")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;
    private final EmployeeService employeeService;
    private final RatingService ratingService;
    @GetMapping
    public String managerDashboard(Model model, Principal principal) {
        Manager manager = managerService.findByUsername(principal.getName());

        // Вычислить среднюю оценку менеджера
        managerService.updateAverageRating(manager.getId());

        model.addAttribute("manager", manager);
        List<Employee> myEmployees = employeeService.findEmployeesByManager(manager);

        // Вычислить средние оценки для каждого работника
        for (Employee employee : myEmployees) {
            employeeService.updateAverageRating(employee.getId());
        }

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
    @PostMapping("/rate-employee")
    public String rateEmployee(@RequestParam Long employeeId, @RequestParam int score, Principal principal) {
        Manager manager = managerService.findByUsername(principal.getName());
        Employee employee = employeeService.findById(employeeId).get();

        // Проверка, что работник принадлежит менеджеру
        if (employee != null && employee.getManager() == manager) {
            Rating rating = new Rating();
            rating.setScore(score);
            rating.setRatedUser(employee);
            rating.setRatedByUser(manager);

            ratingService.save(rating);
        }

        return "redirect:/managers";
    }

}
