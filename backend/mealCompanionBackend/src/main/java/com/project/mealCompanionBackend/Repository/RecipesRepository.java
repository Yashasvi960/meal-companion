package com.project.mealCompanionBackend.Repository;

import com.project.mealCompanionBackend.Entities.RecipesEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecipesRepository extends
        JpaRepository<RecipesEntity, String> {

//    @EntityGraph(attributePaths = {"recipes"})
    Optional<RecipesEntity> findById(UUID id);
}
