package com.project.mealCompanionBackend.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "canonical_ingredients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CanonicalIngredientsEntity {

    @Id
    private String name;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "base_unit", nullable = false)
    private String base_unit;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(columnDefinition = "jsonb")
    private String nutrients;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
    }


}
