package com.project.mealCompanionBackend.Controller;

import com.project.mealCompanionBackend.Entities.CanonicalIngredientsEntity;
import com.project.mealCompanionBackend.Repository.CanonicalIngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/canonical-ingredients")
@RequiredArgsConstructor
public class CanonicalIngredientController {

    private final CanonicalIngredientRepository canonicalIngredientRepository;

    @GetMapping
    public List<CanonicalIngredientsEntity> getAllCanonicalIngredients() {
        return canonicalIngredientRepository.findAll();
    }


}
