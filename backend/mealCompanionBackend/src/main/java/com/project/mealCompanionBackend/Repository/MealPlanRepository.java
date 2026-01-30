package com.project.mealCompanionBackend.Repository;

import com.project.mealCompanionBackend.Entities.MealPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MealPlanRepository extends JpaRepository<MealPlanEntity, UUID> {

     Optional<MealPlanEntity> findById(UUID mealPlanId);
}
