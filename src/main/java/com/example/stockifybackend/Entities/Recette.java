package com.example.stockifybackend.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Recette implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String intitule;
    private String description;
    private int dureeTotal;

    @Column(columnDefinition = "TEXT")
    private String instructionsDePreparation;


    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<String> instructionsList;

    public String getInstructionsDePreparation() {
        return instructionsDePreparation;
    }

    public void setInstructionsDePreparation(String instructionsDePreparation) {
        this.instructionsDePreparation = instructionsDePreparation;
        try {
            this.instructionsList = new ObjectMapper().readValue(instructionsDePreparation, new TypeReference<List<String>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getInstructionsList() {
        return instructionsList;
    }

    public void setInstructionsList(List<String> instructionsList) {
        this.instructionsList = instructionsList;
        try {
            this.instructionsDePreparation = new ObjectMapper().writeValueAsString(instructionsList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    private String imageUrl;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "valeur_nutritionnel_id")
    private ValeurNutritionnel valeurNutritionnel;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private CategorieDeRecette categorieDeRecette;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @OneToMany(mappedBy = "recette", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Ingredient> ingredients;

    private int is_deleted = 0;

    public Recette(String intitule, String description, int dureeTotal, Stock stock, List<Ingredient> ingredient, int is_deleted) {
        this.intitule = intitule;
        this.description = description;
        this.dureeTotal = dureeTotal;
        this.stock = stock;
        this.ingredients = ingredient != null ? ingredient : new ArrayList<>();
        this.is_deleted = is_deleted;
    }

    @OneToMany(mappedBy = "recette")
    @JsonManagedReference
    public List<Ingredient> getIngredients() {
        return ingredients;
    }



}





