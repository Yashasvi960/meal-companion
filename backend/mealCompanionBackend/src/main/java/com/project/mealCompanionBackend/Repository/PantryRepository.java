package com.project.mealCompanionBackend.Repository;

import com.project.mealCompanionBackend.Entities.PantryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PantryRepository extends JpaRepository<PantryEntity, String> {
    List<PantryEntity> findByClientId(String clientId);
}
