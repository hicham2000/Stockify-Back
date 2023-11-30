package com.example.stockifybackend.Entities;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "produit")
public class Produit implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String intitule;
    private String description;
    private String brande;
    private String uniteDeMesure;
    private Date dateExpiration;

    private Number quantite;
    private Number prix;
    private Number quantiteCritique;
    @OneToOne
    private ValeurNutritionnel valeurNutritionnel;
    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<CategorieDeProduits> categories =  new ArrayList<>();

    public Produit(String name) {
        this.intitule = name;
    }
}








