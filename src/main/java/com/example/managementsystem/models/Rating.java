package com.example.managementsystem.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "integer default 0")
    private int score;

    @ManyToOne
    private User ratedUser; // Оцениваемый пользователь (менеджер или работник)

    @ManyToOne
    private User ratedByUser; // Пользователь, который выставил оценку

}


