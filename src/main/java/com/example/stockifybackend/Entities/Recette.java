package com.example.stockifybackend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
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
    private List<String> InstructionsDePreparation;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private CategorieDeRecette categorieDeRecette;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;
    @OneToMany(mappedBy = "recette", cascade = CascadeType.ALL)
    public List<Ingredient> ingredients;
    @ManyToOne
    @JoinColumn(name = "recommendation_id")
    private Recommendation recommendation;

    private int is_deleted = 0;

    public Recette(String intitule, String description, int dureeTotal, Stock stock, List<Ingredient> ingredient, Recommendation recommendation, int is_deleted) {
        this.intitule = intitule;
        this.description = description;
        this.dureeTotal = dureeTotal;
        this.stock = stock;
        this.ingredients = ingredient;
        this.recommendation = recommendation;
        this.is_deleted = is_deleted;
    }

}





