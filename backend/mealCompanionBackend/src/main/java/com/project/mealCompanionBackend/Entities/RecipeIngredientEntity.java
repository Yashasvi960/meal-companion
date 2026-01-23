package com.project.mealCompanionBackend.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "recipe_ingredients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeIngredientEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "recipe_id", nullable = false)
    private UUID recipeId;

    @Column(name = "raw_text", nullable = false)
    private String rawText;

    @Column(name = "canonical_name")
    private String canonicalName;

    @Column(name = "qty_base")
    private BigDecimal qtyBase;

    @Column(name = "base_unit")
    private String baseUnit;

    @Column(name = "notes")
    private String notes;
}
