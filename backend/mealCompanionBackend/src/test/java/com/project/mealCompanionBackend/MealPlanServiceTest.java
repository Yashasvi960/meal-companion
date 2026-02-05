package com.project.mealCompanionBackend;

import com.project.mealCompanionBackend.DTOs.MealPlanRequest;
import com.project.mealCompanionBackend.DTOs.MealPlanResponse;
import com.project.mealCompanionBackend.Entities.*;
import com.project.mealCompanionBackend.Repository.MealPlanItemsRepository;
import com.project.mealCompanionBackend.Repository.MealPlanRepository;
import com.project.mealCompanionBackend.Repository.RecipesRepository;
import com.project.mealCompanionBackend.Services.MealPlanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MealPlanServiceTest {

    @Mock
    private RecipesRepository recipesRepository;

    @Mock
    private MealPlanRepository mealPlanRepository;

    @Mock
    private MealPlanItemsRepository mealPlanItemsRepository;

    @InjectMocks
    private MealPlanService mealPlanService;

    @Test
    void testGenerateMealPlan_noDb() {
        // arrange
        RecipesEntity r1 = RecipesEntity.builder()
                .id(UUID.randomUUID())
                .title("Recipe 1")
                .servings(1)
                .steps("[]")
                .build();
        RecipesEntity r2 = RecipesEntity.builder()
                .id(UUID.randomUUID())
                .title("Recipe 2")
                .servings(1)
                .steps("[]")
                .build();

        RecipeIngredientEntity ri1 = RecipeIngredientEntity.builder()
                .id(UUID.randomUUID())
                .recipe(r1)
                .canonicalName("peanut")
                .build();
        RecipeIngredientEntity ri2 = RecipeIngredientEntity.builder()
                .id(UUID.randomUUID())
                .recipe(r2)
                .canonicalName("lentils")
                .build();

        r1.setRecipeIngredientList(List.of(ri1));
        r2.setRecipeIngredientList(List.of(ri2));

        when(recipesRepository.findAll()).thenReturn(List.of(r1, r2));

        // Make save return the passed entity but with an id set (simulate DB-generated id)
        when(mealPlanRepository.save(any(MealPlanEntity.class))).thenAnswer(inv -> {
            MealPlanEntity mp = inv.getArgument(0);
            if (mp.getId() == null) mp.setId(UUID.randomUUID());
            return mp;
        });

        when(mealPlanItemsRepository.save(any(MealPlanItemsEntity.class))).thenAnswer(inv -> {
            MealPlanItemsEntity item = inv.getArgument(0);
            if (item.getId() == null) item.setId(UUID.randomUUID());
            return item;
        });

        MealPlanRequest request = MealPlanRequest.builder()
                .clientId("test-client-1")
                .startDate(LocalDate.of(2024, 1, 1))
                .days(3)
                .servings(1)
                .constraints(MealPlanRequest.Constraints.builder()
                        .avoidIngredients(List.of("peanut"))
                        .build())
                .build();

        // act
        MealPlanResponse response = mealPlanService.generate(request);

        // assert
        assertNotNull(response);
        assertEquals("test-client-1", response.getClientId());
        assertEquals(3, response.getDays());
        assertEquals(1, response.getServings());
        assertNotNull(response.getMealPlanId());
        assertEquals(2, /* possible picks size may be 2 or 3 depending on shuffle; at least >0 */ response.getMeals().size(), response.getMeals().size());
        verify(recipesRepository, times(1)).findAll();

        // verify that mealPlanItems were saved
        ArgumentCaptor<MealPlanItemsEntity> itemsCaptor = ArgumentCaptor.forClass(MealPlanItemsEntity.class);
        verify(mealPlanItemsRepository, atLeastOnce()).save(itemsCaptor.capture());
        List<MealPlanItemsEntity> savedItems = itemsCaptor.getAllValues();
        assertTrue(savedItems.size() >= 1);
        // each saved item should reference the saved meal plan id
        for (MealPlanItemsEntity it : savedItems) {
            assertNotNull(it.getMealPlanId());
        }
    }
}
