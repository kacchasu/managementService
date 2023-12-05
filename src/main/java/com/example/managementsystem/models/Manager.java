package com.example.managementsystem.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity
@Data
public class Manager extends User {

    @OneToMany(mappedBy = "manager")
    private List<Employee> employees;

    public Manager() {}

    public Manager(String username, String password) {
        super(username, password);
    }

}

