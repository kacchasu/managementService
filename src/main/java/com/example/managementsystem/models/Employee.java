package com.example.managementsystem.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Employee extends User {

    private String position;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @OneToMany(mappedBy = "ratedUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> receivedRatings; // Оценки, полученные работником

    private Double averageRating; // Средняя оценка работника

    public Employee() {
    }

    public Employee(String username, String password) {
        super(username, password);
    }

}




