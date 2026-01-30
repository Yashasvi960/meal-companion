package com.project.mealCompanionBackend.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "meal_plans")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealPlanEntity {

    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "days", nullable = false)
    private Integer days;

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
