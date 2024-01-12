package com.example.stockifybackend.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
public class Ingredient implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String intitule;

    @ManyToOne
    @JoinColumn(name = "recette_id")
    @JsonBackReference
    private Recette recette;

    private Double quantity;
    private String uniteDeMesure;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToOne
    @JoinColumn(name = "repas_id")
    private Repas repas;


    @ManyToOne
    @JoinColumn(name = "recette_id")
    @JsonBackReference
    public Recette getRecette() {
        return recette;
    }

}
