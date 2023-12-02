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
public class Stock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantiteCritiqueParDefaut;
    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
    public List<Recette> recette;
    @OneToMany(mappedBy = "stock")
    public List<Produit> produit;

}

