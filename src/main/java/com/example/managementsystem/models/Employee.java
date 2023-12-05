package com.example.managementsystem.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Employee extends User {

    private String position;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    public Employee() {}

    public Employee(String username, String password) {
        super(username, password);
    }
}




