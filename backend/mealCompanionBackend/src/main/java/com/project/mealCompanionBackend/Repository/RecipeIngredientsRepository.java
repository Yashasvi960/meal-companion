package com.project.mealCompanionBackend.Repository;

import com.project.mealCompanionBackend.Entities.RecipeIngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientsRepository extends JpaRepository<RecipeIngredientEntity, String> {


}
