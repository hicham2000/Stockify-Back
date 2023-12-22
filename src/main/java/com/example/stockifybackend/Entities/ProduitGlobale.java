package com.example.stockifybackend.Entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "produit_globale")
public class ProduitGlobale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String intitule;
    private String image;
    private String uniteDeMesure;
    private String portion;
    private Long calorie;
    private Long protein;
    private Long carbs;
    private Long lipides;
    private Long sucre;
    private Long fibre;

}