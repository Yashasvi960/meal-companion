package com.project.mealCompanionBackend.DTOs;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlannedMealDto {

    private UUID recipeId;
    private LocalDate localDate;
    private Integer servings;

}
