package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.Entities.Recette;
import com.example.stockifybackend.dto.RecetteResponse;
import com.example.stockifybackend.services.RecommendationService;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }


    @GetMapping("/Recettes/{id}")
    public ResponseEntity<?> getRecommendedRecettes(
            @PathVariable Long id
    ) throws JSONException {

        Map<String, Object> response = new HashMap<>();

        List<RecetteResponse> recommendedRecettes = recommendationService.getRecommendedRecettes(id);
        if(recommendedRecettes.isEmpty()) {
            response.put("message", "Aucun Recettes recommandées");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Recettes Recommendées");
        response.put("recettes", recommendedRecettes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/RecettesFiltred/{id}")
    public ResponseEntity<?> getRecommendedFiltredRecettes(
            @PathVariable Long id,
            @RequestBody Map<String, Object> requestBody
    ) throws JSONException {
        Map<String, Object> response = new HashMap<>();

        List<String> régimesSpéciaux = (List<String>) requestBody.get("régimeSpéciaux");
        String tempsDePreparation = (String) requestBody.get("tempsDePreparation");
        List<String> nomsDesIngrédientPréféres = (List<String>) requestBody.get("nomsDesIngrédientPréféres");

        List<RecetteResponse> recommendedRecettes = recommendationService.getRecommendedFilteredRecettes(id, régimesSpéciaux, tempsDePreparation, nomsDesIngrédientPréféres);
        if (recommendedRecettes.isEmpty()) {
            response.put("message", "Aucun Recettes recommandées");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("message", "Recettes Recommendées");
        response.put("recettes", recommendedRecettes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/RecettesSimilaires/{id}")
    public ResponseEntity<Map<String, Object>> getSimilarRecettes(
            @PathVariable Long id,
            @RequestParam Long user_id) throws JSONException {
        Map<String, Object> response = new HashMap<>();
        List<RecetteResponse> similarRecettes = recommendationService.getRecettesSimilaires(id, user_id);

        if (similarRecettes.isEmpty()) {
            response.put("message", "Aucun Recettes similaires");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("message", "Recettes similaires");
        response.put("recettes", similarRecettes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
