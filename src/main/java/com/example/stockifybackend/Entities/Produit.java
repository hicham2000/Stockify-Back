package com.example.stockifybackend.Entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
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
    private String brande;
    private String uniteDeMesure;



    private Long id_produitCourse;

    @JsonFormat(pattern="yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date dateExpiration;
    @JsonFormat(pattern="yyyy.MM.dd")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date dateAlerte;

    private double quantite;
    private double prix;
    private double quantiteCritique;
    @OneToOne
    private ValeurNutritionnel valeurNutritionnel;

    @ManyToOne
    @JoinColumn(name = "categorieDeProduits_id")
    private CategorieDeProduits categories;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @ManyToOne
    @JoinColumn(name = "depense_id")
    private Depense depense;


    private int is_deleted = 0;
    private int permanent = 0;
    private int gaspille = 0;
    private String imageUrl;


    public Produit(String intitule, String description, String brande, String uniteDeMesure, Date dateExpiration, Date dateAlerte,
                   double quantite, double prix, double quantiteCritique, ValeurNutritionnel valeurNutritionnel,
                   CategorieDeProduits categories, Stock stock, Depense depense, int is_deleted , int permanent , int gaspille , String imageUrl) {
        this.intitule = intitule;
        this.brande = brande;
        this.uniteDeMesure = uniteDeMesure;
        this.dateExpiration = dateExpiration;
        this.dateAlerte = dateAlerte;
        this.quantite = quantite;
        this.prix = prix;
        this.quantiteCritique = quantiteCritique;
        this.valeurNutritionnel = valeurNutritionnel;
        this.categories = categories;
        this.stock = stock;
        this.depense = depense;
        this.is_deleted = is_deleted;
        this.permanent = permanent;
        this.gaspille = gaspille;
        this.imageUrl = imageUrl;
//        this.recommendation = recommendation;


    }

    public Produit(String name) {
        this.intitule = name;
    }

    public void removeCategories() {
        this.categories = null;
    }

    public void removeStock() {
        this.stock = null;
    }

    public void removeDepense() {
        this.depense = null;
    }
}








