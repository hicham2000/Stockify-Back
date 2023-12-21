package com.example.stockifybackend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProduitAAcheter implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String intitule;
    private String uniteDeMesure;
    private double quantite;
    private boolean etat;
    @ManyToOne
    @JoinColumn(name = "listecourse_id" )
    private ListeCourse listeCourse;

    public ProduitAAcheter(String intitule, String uniteDeMesure, double quantite, boolean etat) {
        this.intitule = intitule;
        this.uniteDeMesure = uniteDeMesure;
        this.quantite = quantite;
        this.etat = etat;
        this.listeCourse = new ListeCourse();
    }

}
