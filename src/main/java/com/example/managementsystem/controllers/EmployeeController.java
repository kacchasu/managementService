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
import java.util.List;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final ManagerService managerService;
    private final RatingService ratingService;

    @GetMapping("/info")
    public String employeeInfo(Model model, Principal principal) {
        Employee employee = employeeService.findByUsername(principal.getName());

        // Вычислить среднюю оценку работника
        employeeService.updateAverageRating(employee.getId());

        model.addAttribute("employee", employee);
        return "employee/info"; // Thymeleaf шаблон для информации о работнике
    }


    @PostMapping("/leave")
    public String leaveJob(@RequestParam Long employeeId) {
        // Увольнение сотрудника
        employeeService.deleteById(employeeId);
        return "redirect:/login"; // После увольнения перенаправить на выход из аккаунта
    }
    @PostMapping("/rate-manager")
    public String rateManager(@RequestParam Long managerId, @RequestParam int score, Principal principal) {
        Employee employee = employeeService.findByUsername(principal.getName());
        Manager manager = employee.getManager();

        // Проверка, что у работника есть менеджер
        if (manager != null) {
            Rating rating = new Rating();
            rating.setScore(score);
            rating.setRatedUser(manager);
            rating.setRatedByUser(employee);

            ratingService.save(rating);
        }

        return "redirect:/employees/info";
    }



}
