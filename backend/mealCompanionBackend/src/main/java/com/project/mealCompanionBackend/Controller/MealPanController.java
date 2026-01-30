package com.project.mealCompanionBackend.Controller;

import com.project.mealCompanionBackend.DTOs.MealPlanRequest;
import com.project.mealCompanionBackend.DTOs.MealPlanResponse;
import com.project.mealCompanionBackend.Services.MealPlanService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mealplan")
public class MealPanController {

    private final MealPlanService mealPlanService;

    @PostMapping("/generate")
    public ResponseEntity<MealPlanResponse> generateMealPlan(@RequestBody MealPlanRequest request) {
        if(request.getClientId()==null || request.getClientId().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        MealPlanResponse response = mealPlanService.generate(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealPlanResponse> fetchMealPlan(@PathVariable UUID id) {
        MealPlanResponse response = mealPlanService.fetchMealPlan(id);
        if(response==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }



}
