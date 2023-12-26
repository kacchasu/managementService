package com.example.managementsystem.repositories;

import com.example.managementsystem.models.Employee;
import com.example.managementsystem.models.Rating;
import com.example.managementsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByRatedByUser(User ratedByUser);

    List<Rating> findByRatedUser(User ratedUser);

    void deleteAllByRatedUser(Employee employee);

    void deleteAllByRatedByUser(Employee employee);

    void deleteByRatedUser(Employee employee);

    void deleteByRatedByUser(Employee employee);
}

