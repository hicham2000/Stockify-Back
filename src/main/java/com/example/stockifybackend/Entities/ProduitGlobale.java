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

    @Column(name = "`unite_de_mesure`")
    private String uniteDeMesure;

    @Column(name = "`portion`", length = 255)
    private String portion;

    private Double calorie;
    private Double protein;
    private Double carbs;
    private Double lipides;
    private Double sucre;
    private Double fibre;
    private String imageUrl;

}