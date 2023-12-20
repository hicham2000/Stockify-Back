package com.example.stockifybackend.Entities;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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

    private double quantite;
    private double prix;
    private double quantiteCritique;
    @OneToOne
    private ValeurNutritionnel valeurNutritionnel;
    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<CategorieDeProduits> categories =  new ArrayList<>();
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;
    @ManyToOne
    @JoinColumn(name = "depense_id")
    private Depense depense;
    @ManyToOne
    @JoinColumn(name = "recommendation_id")
    private Recommendation recommendation;

    @ManyToOne
    @JoinColumn(name = "listecourse_id" )
    private ListeCourse listeCourse;

    private int is_deleted = 0;


    public Produit(String name) {
        this.intitule = name;
    }
}








