package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.Entities.Recette;
import com.example.stockifybackend.dto.RecetteResponse;
import com.example.stockifybackend.services.RecommendationService;
import lombok.NoArgsConstructor;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }


    @GetMapping("/Recettes/{id}")
    public ResponseEntity<?> getRecommendedRecettes(
            @PathVariable Long id,
            @RequestParam(name = "tempsDuClient", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime tempsDuClient
    ) throws JSONException {

        Map<String, Object> response = new HashMap<>();

        if (tempsDuClient == null) {
            tempsDuClient = LocalDateTime.now();
        }

        List<RecetteResponse> recommendedRecettes = recommendationService.getRecommendedRecettes(id, tempsDuClient);
        if(recommendedRecettes.isEmpty()) {
            response.put("message", "Aucun Recettes recommandées");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Recettes Recommendées");
        response.put("recettes", recommendedRecettes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
