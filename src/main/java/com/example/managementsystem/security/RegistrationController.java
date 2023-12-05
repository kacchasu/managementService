package com.example.managementsystem.security;

import com.example.managementsystem.models.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;


    @GetMapping
    public String showRegistrationForm() {
        return "registration"; // Thymeleaf шаблон для регистрации
    }

    @PostMapping
    public String registerUser(@ModelAttribute UserDto userDto) {
        registrationService.register(userDto);
        return "redirect:/login";
    }
}
