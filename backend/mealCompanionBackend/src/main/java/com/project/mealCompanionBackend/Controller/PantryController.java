package com.project.mealCompanionBackend.Controller;

import com.project.mealCompanionBackend.Entities.PantryEntity;
import com.project.mealCompanionBackend.Repository.PantryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pantry")
@RequiredArgsConstructor
public class PantryController {

    private final PantryRepository pantryRepository;

    @PostMapping
    public ResponseEntity<PantryEntity> createPantryItem(@RequestBody PantryEntity pantryEntity) {
        PantryEntity savedItem = pantryRepository.save(pantryEntity);
        return ResponseEntity.ok(savedItem);
    }

    @GetMapping("/{clientId}")
    public List<PantryEntity> getPantryItems(@PathVariable String clientId) {
        return pantryRepository.findByClientId(clientId);
    }
}
