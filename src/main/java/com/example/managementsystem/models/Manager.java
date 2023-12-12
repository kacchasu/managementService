package com.example.managementsystem.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Manager extends User {

    @OneToMany(mappedBy = "manager")
    private List<Employee> employees;

    @OneToMany(mappedBy = "ratedByUser")
    private List<Rating> givenRatings; // Оценки, выставленные менеджером

    private Double averageRating; // Средняя оценка менеджера

    public Manager() {
    }

    public Manager(String username, String password) {
        super(username, password);
    }

}