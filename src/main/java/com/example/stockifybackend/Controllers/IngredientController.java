package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.Entities.Ingredient;
import com.example.stockifybackend.Entities.Recette;
import com.example.stockifybackend.Repositories.IngredientRepository;
import com.example.stockifybackend.Repositories.RecetteRepository;
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
        }

        response.put("message", "Aucun Ingredient avec id=" + ingredientId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/Ingredients")
    public ResponseEntity<?> getAllIngredients() {
        Map<String, Object> response = new HashMap<>();
        List<Ingredient> ingredients = new ArrayList<>();//ingredientRepository.findAll();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setIntitule("Ingredient");
        ingredient.setQuantity(4.0);
        ingredient.setRepas(null);
        ingredient.setRecette(null);
        ingredients.add(ingredient) ;

        Ingredient ingredient1 = new Ingredient();
        ingredient.setId(2L);
        ingredient.setIntitule("Ingredient");
        ingredient.setQuantity(4.0);
        ingredient.setRepas(null);
        ingredient.setRecette(null);
        ingredients.add(ingredient) ;

        Ingredient ingredient2 = new Ingredient();
        ingredient.setId(3L);
        ingredient.setIntitule("Ingredient");
        ingredient.setQuantity(4.0);
        ingredient.setRepas(null);
        ingredient.setRecette(null);
        ingredients.add(ingredient) ;
        try {
            response.put("message", "Ingredients récupérés par succès");
            response.put("ingredients", ingredients);
        } catch (Exception e){
            response.put("message", "Erreur lors du récupération d'ingrédients: " + e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
