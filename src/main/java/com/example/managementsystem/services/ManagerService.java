package com.example.managementsystem.services;

import com.example.managementsystem.models.Manager;
import com.example.managementsystem.models.Rating;
import com.example.managementsystem.repositories.ManagerRepository;
import com.example.managementsystem.repositories.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final RatingRepository ratingRepository;
    private final RatingService ratingService;

    public Manager findByUsername(String username) {
        return managerRepository.findByUsername(username);
    }



    public void updateAverageRating(Long managerId) {
        Manager manager = managerRepository.findById(managerId).orElseThrow(() -> new RuntimeException("Менеджер не найден"));
        List<Rating> ratings = ratingService.findByRatedUser(manager);

        if (!ratings.isEmpty()) {
            double totalScore = ratings.stream().mapToDouble(Rating::getScore).sum();
            double averageScore = totalScore / ratings.size();
            manager.setAverageRating(averageScore);
            managerRepository.save(manager);
        }
    }}
