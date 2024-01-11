package com.example.stockifybackend.dto;  // Changez le package en conséquence

import com.example.stockifybackend.Entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecetteResponse implements Serializable {
    private Long id;
    private String intitule;
    private String description;
    private int dureeTotal;
    private String imageUrl;
    private ValeurNutritionnel valeurNutritionnel;
    private CategorieDeRecette categorieDeRecette;
    private List<String> instructionsList = new ArrayList<>();
    private int quantiteEnStock;
    private int nombreIngredientsManquantes;
    private List<IngredientInfo> ingredients = new ArrayList<>();
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

    public RecetteResponse(Recette recette) {
        this.id = recette.getId();
        this.intitule = recette.getIntitule();
        this.description = recette.getDescription();
        this.dureeTotal = recette.getDureeTotal();
        this.instructionsList = recette.getInstructionsList();
        this.imageUrl = recette.getImageUrl();
        this.valeurNutritionnel = recette.getValeurNutritionnel();
        this.categorieDeRecette = recette.getCategorieDeRecette();
    }

    public void setQuantiteEnStock(List<Recette> stockRecette) {
        this.quantiteEnStock = quantiteRecetteEnStock(this.intitule, stockRecette);
    }

    public void setNombreIngredientManquantes() {
        this.nombreIngredientsManquantes = CalculerNombreIngredientsManquantes();
    }

    public void setIngredients(List<Ingredient> recettesIngredients, List<Produit> stockProduits) {
        this.ingredients = convertIngredients(recettesIngredients, stockProduits);
    }

    public void setIsFavoris(Utilisateur utilisateur) {
        this.isFavoris = recetteIsFavorisDeUtilisateur(utilisateur);
    }

    private List<IngredientInfo> convertIngredients(List<Ingredient> recetteIngredients, List<Produit> stockProduits) {
        return recetteIngredients.stream()
                .map(ingredient -> {
                    boolean isEnough = isIngredientEnoughInStock(ingredient, stockProduits);
                    return new IngredientInfo(
                            ingredient.getId(),
                            ingredient.getIntitule(),
                            ingredient.getQuantity(),
                            isEnough
                    );
                })
                .collect(Collectors.toList());
    }

    private boolean isIngredientEnoughInStock(Ingredient ingredient, List<Produit> stockProduits) {
        String ingredientName = ingredient.getIntitule().toLowerCase().strip();

        // Vérifier si le nom de l'ingrédient existe dans la liste des produits du stock
        return stockProduits.stream()
                .anyMatch(produit -> produit.getIntitule().toLowerCase().strip().equals(ingredientName) && produit.getQuantite() >= ingredient.getQuantity());
    }

    private int quantiteRecetteEnStock(String intituleRecette, List<Recette> stockRecette) {
        // Filtrer les Recette du stock dont l'intitulé correspond à l'intitulé de la recette
        long quantiteRecettesCorrespondantes = stockRecette.stream()
                .filter(Recette -> Recette.getIntitule().equalsIgnoreCase(intituleRecette))
                .count();

        return (int) quantiteRecettesCorrespondantes;
    }

    private Double quantiteIngredientEnStock(Ingredient ingredient, List<Produit> stockProduits) {
        String ingredientName = ingredient.getIntitule().toLowerCase().strip();

        // Rechercher le produit correspondant au nom de l'ingrédient dans la liste de produits du stock
        Optional<Produit> produitOptional = stockProduits.stream()
                .filter(produit -> produit.getIntitule().toLowerCase().strip().equals(ingredientName))
                .findFirst();

        // Si le produit correspondant est trouvé, retourner la quantité en stock, sinon retourner 0
        return produitOptional.map(Produit::getQuantite).orElse(Double.valueOf(0));
    }

    public int CalculerNombreIngredientsManquantes() {
        if (this.ingredients == null || this.ingredients.isEmpty()) {
            return -1;
        }

        long countNotEnough = this.ingredients.stream()
                .filter(ingredient -> !ingredient.isEnough())
                .count();

        return (int) countNotEnough;
    }

    public boolean recetteIsFavorisDeUtilisateur(Utilisateur utilisateur) {
        List<Recette> recettesFavorites = utilisateur.getRecettesFavoris();
        return recettesFavorites.stream()
                .anyMatch(recette -> recette.getId().equals(this.id));
    }


}
