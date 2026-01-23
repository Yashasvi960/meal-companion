package com.project.mealCompanionBackend.Repository;

import com.project.mealCompanionBackend.Entities.CanonicalIngredientsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanonicalIngredientRepository extends JpaRepository
        <CanonicalIngredientsEntity, String> {


}
