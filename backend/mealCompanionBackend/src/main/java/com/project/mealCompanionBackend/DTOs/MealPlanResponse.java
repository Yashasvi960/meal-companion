package com.project.mealCompanionBackend.DTOs;


import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealPlanResponse {

    private UUID mealPlanId;
    private String clientId;
    private LocalDate startDate;
    private Integer days;
    private Integer servings;
    private List<PlannedMealDto> meals;
}
