package com.project.mealCompanionBackend.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "meal_plan_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealPlanItemsEntity {

    @Id
    private UUID id;

    @Column(name = "meal_plan_id", nullable = false)
    private UUID mealPlanId;

    @Column(name = "day_index", nullable = false)
    private Integer dayIndex;

    @Column(name = "recipe_id", nullable = false)
    private UUID recipeId;

    @Column(name = "servings", nullable = false)
    private Integer servings;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @PrePersist
    public void pre() {
        if (this.id == null) this.id = UUID.randomUUID();
        if (this.createdAt == null) this.createdAt = Instant.now();
    }
}
