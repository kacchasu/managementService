package com.example.managementsystem.services;

import com.example.managementsystem.models.Employee;
import com.example.managementsystem.models.Manager;
import com.example.managementsystem.models.Rating;
import com.example.managementsystem.models.User;
import com.example.managementsystem.repositories.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;

    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }

    public List<Rating> findByRatedUser(User ratedUser) {
        return ratingRepository.findByRatedUser(ratedUser);
    }

    public List<Rating> findByRatedByUser(User ratedByUser) {
        return ratingRepository.findByRatedByUser(ratedByUser);
    }

    public Rating findRatingByRatedUser(Employee employee) {
        return null;
    }
}
