package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.Entities.Recette;
import com.example.stockifybackend.Entities.Recommendation;
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

    @GetMapping
    public ResponseEntity<List<Recommendation>> getAllRecommendations() {
        List<Recommendation> recommendations = recommendationService.getAllRecommendations();
        return new ResponseEntity<>(recommendations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recommendation> getRecommendationById(@PathVariable Long id) {
        Optional<Recommendation> recommendation = recommendationService.getRecommendationById(id);
        return recommendation.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Recommendation> addRecommendation(@RequestBody Recommendation recommendation) {
        Recommendation savedRecommendation = recommendationService.saveRecommendation(recommendation);
        return new ResponseEntity<>(savedRecommendation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recommendation> updateRecommendation(@PathVariable Long id, @RequestBody Recommendation recommendation) {
        if (recommendationService.getRecommendationById(id).isPresent()) {
            recommendation.setId(id);
            Recommendation updatedRecommendation = recommendationService.saveRecommendation(recommendation);
            return new ResponseEntity<>(updatedRecommendation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecommendation(@PathVariable Long id) {
        if (recommendationService.getRecommendationById(id).isPresent()) {
            recommendationService.deleteRecommendation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

        List<Recette> recommendedRecettes = recommendationService.getRecommendedRecettes(id, tempsDuClient);
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
            @RequestParam(name = "tempsDuClient", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime tempsDuClient,
            @RequestBody Map<String, Object> requestBody
    ) throws JSONException {
        Map<String, Object> response = new HashMap<>();

        if (tempsDuClient == null) {
            tempsDuClient = LocalDateTime.now();
        }

        String régimeSpéciale = (String) requestBody.get("régimeSpéciale");
        String tempsDePreparation = (String) requestBody.get("tempsDePreparation");
        List<String> nomsDesIngrédientPréféres = (List<String>) requestBody.get("nomsDesIngrédientPréféres");

        List<Recette> recommendedRecettes = recommendationService.getRecommendedFilteredRecettes(id, tempsDuClient, régimeSpéciale, tempsDePreparation, nomsDesIngrédientPréféres);
        if (recommendedRecettes.isEmpty()) {
            response.put("message", "Aucun Recettes recommandées");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Recettes Recommendées");
        response.put("recettes", recommendedRecettes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/RecettesSimilaires/{id}")
    public ResponseEntity<?> getSimilarRecettes(@PathVariable Long id) throws JSONException {
        Map<String, Object> response = new HashMap<>();

        List<Recette> similarRecettes = recommendationService.getRecettesSimilaires(id);
        if (similarRecettes.isEmpty()) {
            response.put("message", "Aucun Recettes similaires");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Recettes similaires");
        response.put("recettes", similarRecettes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
