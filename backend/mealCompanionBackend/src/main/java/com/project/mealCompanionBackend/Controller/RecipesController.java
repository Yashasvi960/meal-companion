package com.project.mealCompanionBackend.Controller;

import com.project.mealCompanionBackend.Entities.RecipesEntity;
import com.project.mealCompanionBackend.Repository.RecipesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipesController {

    private final RecipesRepository recipesRepository;

    @GetMapping
    public List<RecipesEntity> getRecipe() {
        return recipesRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<RecipesEntity> getRecipeById(@PathVariable UUID id) {
        return recipesRepository.findById(id);
    }
}
