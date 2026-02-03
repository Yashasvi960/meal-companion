package com.project.mealCompanionBackend;

import com.project.mealCompanionBackend.DTOs.MealPlanRequest;
import com.project.mealCompanionBackend.DTOs.MealPlanResponse;
import com.project.mealCompanionBackend.Entities.RecipeIngredientEntity;
import com.project.mealCompanionBackend.Entities.RecipesEntity;
import com.project.mealCompanionBackend.Repository.MealPlanItemsRepository;
import com.project.mealCompanionBackend.Repository.MealPlanRepository;
import com.project.mealCompanionBackend.Repository.RecipesRepository;
import com.project.mealCompanionBackend.Services.MealPlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MealPlanServiceTest {

    @Mock
    private RecipesRepository recipesRepository;

    @Mock
    private MealPlanRepository mealPlanRepository;

    @Mock
    private MealPlanItemsRepository mealPlanItemsRepository;

    @InjectMocks
    private MealPlanService mealPlanService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateMealPlan() {
        RecipesEntity r1 = RecipesEntity.builder().id(UUID.randomUUID()).title("Recipe 1").servings(1).steps("[]").build();
        RecipesEntity r2 = RecipesEntity.builder().id(UUID.randomUUID()).title("Recipe 2").servings(1).steps("[]").build();

        RecipeIngredientEntity ri1 = RecipeIngredientEntity.builder().id(UUID.randomUUID()).recipe(r1).canonicalName("peanut").build();
        RecipeIngredientEntity ri2 = RecipeIngredientEntity.builder().id(UUID.randomUUID()).recipe(r2).canonicalName("lentils").build();

        r1.setRecipeIngredientList(List.of(ri1));
        r2.setRecipeIngredientList(List.of(ri2));

        when(recipesRepository.findAll()).thenReturn(List.of(r1, r2));

        // Implement test logic for meal plan generation
        MealPlanRequest request = MealPlanRequest.builder()
                .clientId("test-client-1")
                .startDate(LocalDate.of(2024, 1, 1))
                .days(3)
                .servings(1)
                .constraints(MealPlanRequest.Constraints.builder()
                        .avoidIngredients(List.of("peanut"))
                        .build()).build();

        MealPlanResponse response = mealPlanService.generate(request);
    }
}
