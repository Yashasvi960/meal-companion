package com.project.mealCompanionBackend.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "pantry_items")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PantryEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Column(name = "canonical_name")
    private String canonicalName;

    @Column(name = "qty_base", nullable = false)
    private BigDecimal qtyBase;

    @Column(name = "base_unit", nullable = false)
    private String baseUnit;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    public void setUpdatedAt() {
        this.updatedAt = Instant.now();
    }
}
