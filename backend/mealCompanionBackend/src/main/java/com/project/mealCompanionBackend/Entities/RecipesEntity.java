package com.project.mealCompanionBackend.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipesEntity {

    @Id
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column( name = "servings", nullable = false)
    private int servings;

    @Column(name = "steps", nullable = false)
    private String steps;

    @Column(name = "tags")
    private String tags;

    @Column(name = "nutrients_summary")
    private String nutrients_summary;

    @Column(name = "created_at", updatable = false)
    private Instant created_at;

    @PrePersist

    public void onCreate() {
        this.created_at = Instant.now();
    }
}
