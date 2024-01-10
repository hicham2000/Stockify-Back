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
    private String desctipion;
    private int dureeTotal;
    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;
    @OneToMany(mappedBy = "recette", cascade = CascadeType.ALL)
    public List<Ingredient> ingredient;
    @ManyToOne
    @JoinColumn(name = "recommendation_id")
    private Recommendation recommendation;

    private int is_deleted = 0;

    public Recette(String intitule, String desctipion, int dureeTotal, Stock stock, List<Ingredient> ingredient, Recommendation recommendation, int is_deleted) {
        this.intitule = intitule;
        this.desctipion = desctipion;
        this.dureeTotal = dureeTotal;
        this.stock = stock;
        this.ingredient = ingredient;
        this.recommendation = recommendation;
        this.is_deleted = is_deleted;
    }

}





