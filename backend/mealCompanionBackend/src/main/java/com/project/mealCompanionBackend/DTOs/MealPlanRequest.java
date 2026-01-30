package com.project.mealCompanionBackend.DTOs;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealPlanRequest {

    private String clientId;
    private LocalDate startDate;
    private Integer days;
    private Integer servings;
    private Constraints constraints;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Constraints {
        private Integer maxCookTime;
        private List<String> avoidIngredients;

        private String focusNutrition;
    }

}
