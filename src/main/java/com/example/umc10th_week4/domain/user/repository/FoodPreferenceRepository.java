package com.example.umc10th_week4.domain.user.repository;

import com.example.umc10th_week4.domain.user.entity.FoodPreference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodPreferenceRepository extends JpaRepository<FoodPreference, Long> {
}