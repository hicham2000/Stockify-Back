package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.Entities.Ingredient;
import com.example.stockifybackend.Repositories.IngredientRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api")
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping("/Ingredient/{ingredientId}")
    public ResponseEntity<?> getIngredientByID(@PathVariable Long ingredientId) {
        Map<String, Object> response = new HashMap<>();
        Optional<Ingredient> OptionalIngredient = ingredientRepository.findById(ingredientId);

        if(OptionalIngredient.isPresent()){
            Ingredient ingredient = OptionalIngredient.get();
            response.put("message", "Ingredient récupérer par succès");
            response.put("ingredient", ingredient);
        }else {
            response.put("message", "Aucun Ingredient avec id=" + ingredientId);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/Ingredients")
    public ResponseEntity<?> getAllIngredients() {
        Map<String, Object> response = new HashMap<>();
        PageRequest pageRequest = PageRequest.of(0, 100);
        List<Ingredient> ingredients = ingredientRepository.findIngredientRandomly(pageRequest);

        try {
            response.put("message", "Ingredients récupérés par succès");
            response.put("ingredients", ingredients.stream().limit(100));
        } catch (Exception e){
            response.put("message", "Erreur lors du récupération d'ingrédients: " + e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
