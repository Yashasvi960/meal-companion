package com.project.mealCompanionBackend.Repository;

import com.project.mealCompanionBackend.Entities.MealPlanItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MealPlanItemsRepository extends JpaRepository<MealPlanItemsEntity, String> {
    List<MealPlanItemsEntity> findByMealPlanIdOrderByDayIndex(UUID mealPlanId);
}
