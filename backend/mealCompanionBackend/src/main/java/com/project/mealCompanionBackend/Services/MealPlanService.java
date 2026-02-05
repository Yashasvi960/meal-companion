package com.project.mealCompanionBackend.Services;

import com.project.mealCompanionBackend.DTOs.MealPlanRequest;
import com.project.mealCompanionBackend.DTOs.MealPlanResponse;
import com.project.mealCompanionBackend.DTOs.PlannedMealDto;
import com.project.mealCompanionBackend.Entities.*;
import com.project.mealCompanionBackend.Repository.MealPlanItemsRepository;
import com.project.mealCompanionBackend.Repository.MealPlanRepository;
import com.project.mealCompanionBackend.Repository.RecipesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealPlanService {
    private static final Logger logger = LoggerFactory.getLogger(MealPlanService.class);

    private final RecipesRepository recipesRepository;
    private final MealPlanRepository mealPlanRepository;
    private final MealPlanItemsRepository mealPlanItemsRepository;

    public MealPlanResponse generate(MealPlanRequest request) {
        logger.info("Generating meal plan for client: {}", request.getClientId());
        String clientId = request.getClientId();
        LocalDate startDate = request.getStartDate()!=null?request.getStartDate():LocalDate.now();
        Integer days = request.getDays()!=null?request.getDays():7;
        Integer servings = request.getServings()!=null?request.getServings():1;



        List<RecipesEntity> recipesList = recipesRepository.findAll();

        MealPlanRequest.Constraints c= request.getConstraints();
        Set<String> avoidIngredients = c!=null && c.getAvoidIngredients()!=null?
                new HashSet<>(c.getAvoidIngredients()): Collections.emptySet();

        List<RecipesEntity> candidate = new ArrayList<>();
        for(RecipesEntity r: recipesList) {
            boolean hasAvoid = false;
            List<RecipeIngredientEntity> ingredients = r.getRecipeIngredientList()==null?Collections.emptyList():r.getRecipeIngredientList();
            for(RecipeIngredientEntity ri: ingredients) {
                if(avoidIngredients.contains(ri.getCanonicalName())) {
                    hasAvoid = true;
                    break;
                }
            }
            if(!hasAvoid) candidate.add(r);

            if (candidate.isEmpty()) {
                candidate.addAll(recipesList);
            }


            Collections.shuffle(candidate, new Random());

            List<RecipesEntity> picks = new ArrayList<>();
            int idx = 0;
            for (int i = 0; i < days; i++) {
                if (candidate.isEmpty()) break;
                picks.add(candidate.get(idx % candidate.size()));
                idx++;
            }

            MealPlanEntity mealPlan  = MealPlanEntity.builder()
                    .clientId(clientId)
                    .startDate(startDate)
                    .title("Meal Plan for " + clientId)
                    .days(days)
                    .servings(servings)
                    .build();
            mealPlanRepository.save(mealPlan);


            List<PlannedMealDto> plannedMeals = new ArrayList<>();
            for(int dayIndex = 0; dayIndex< picks.size(); dayIndex++) {
                RecipesEntity chosen = picks.get(dayIndex);
                MealPlanItemsEntity item = MealPlanItemsEntity.builder()
                        .mealPlanId(mealPlan.getId())
                        .title(chosen.getTitle())
                        .dayIndex(dayIndex)
                        .recipeId(chosen.getId())
                        .servings(servings)
                        .build();
                mealPlanItemsRepository.save(item);

                plannedMeals.add(PlannedMealDto.builder()
                        .recipeId(chosen.getId())
                        .title(chosen.getTitle())
                        .localDate(startDate.plusDays(dayIndex))
                        .servings(servings)
                        .build());
            }

            MealPlanResponse resp = MealPlanResponse.builder()
                    .mealPlanId(mealPlan.getId())
                    .clientId(clientId)
                    .startDate(startDate)
                    .days(days)
                    .servings(servings)
                    .meals(plannedMeals)
                    .build();
            return resp;
        }

        return null;
    }

    @Transactional()
    public MealPlanResponse fetchMealPlan(UUID mealPlanId) {
        logger.info("Fetching meal plan: {}", mealPlanId);
        Optional<MealPlanEntity> mealPlan = mealPlanRepository.findById(mealPlanId);

        if(mealPlan.isEmpty()) return null;
        MealPlanEntity mp = mealPlan.get();
        List<MealPlanItemsEntity> items = mealPlanItemsRepository.findByMealPlanIdOrderByDayIndex(mp.getId());
        List<PlannedMealDto> planned = items.stream().map(i ->
                PlannedMealDto.builder()
                        .recipeId(i.getRecipeId())
                        .title(i.getTitle())
                        .localDate(mp.getStartDate().plusDays(i.getDayIndex()))
                        .servings(i.getServings())
                        .build()
        ).collect(Collectors.toList());

        return MealPlanResponse.builder()
                .mealPlanId(mp.getId())
                .clientId(mp.getClientId())
                .startDate(mp.getStartDate())
                .days(mp.getDays())
                .servings(mp.getServings())
                .meals(planned)
                .build();
    }


}
