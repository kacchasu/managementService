package com.example.managementsystem.models;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String password;
    private String role; // "EMPLOYEE" или "MANAGER"
}
