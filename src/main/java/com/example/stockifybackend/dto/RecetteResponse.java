package com.example.stockifybackend.dto;  // Changez le package en conséquence

import com.example.stockifybackend.Entities.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecetteResponse implements Serializable {
    private Long id;
    private String intitule;
    private String description;
    private int dureeTotal;
    private List<String> instructionsList;
    private String imageUrl;
    private ValeurNutritionnel valeurNutritionnel;
    private CategorieDeRecette categorieDeRecette;
    private int quantiteEnStock;
    private int nombreIngredientsManquantes;
    private List<IngredientInfo> ingredients;
    private boolean isFavoris;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IngredientInfo {
        private Long id;
        private String intitule;
        private Double quantity;
        private boolean isEnough;
    }

    public RecetteResponse(Recette recette, int quantiteEnStock, int nombreIngredientsManquantes, boolean isFavoris) {
        this.id = recette.getId();
        this.intitule = recette.getIntitule();
        this.description = recette.getDescription();
        this.dureeTotal = recette.getDureeTotal();
        this.instructionsList = recette.getInstructionsList();
        this.imageUrl = recette.getImageUrl();
        this.valeurNutritionnel = recette.getValeurNutritionnel();
        this.categorieDeRecette = recette.getCategorieDeRecette();
        this.quantiteEnStock = quantiteEnStock;
        this.nombreIngredientsManquantes = nombreIngredientsManquantes;
        this.isFavoris = isFavoris;
    }

}
