package com.example.stockifybackend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Utilisateur implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String prénom;
    private String nom;
    private String email;
    private String password;
    private String sexe="Homme";
    private String taille;

    private String poids;
    private Date dateDeNaissance=new Date(2022, 1, 12);
    private String régimeSpécieux;
    private boolean alertedateexpi;
    private boolean alerteproduitfinis;
    private boolean modeSportif;
    private long listeDeCourse_id;
    private String notifToken;



    @OneToMany
    @JoinColumn(name = "id")
    private List<PréférenceAlimentaire> préférenceAlimentaires = new ArrayList<>();

    @OneToMany
    @JoinTable(
            name = "utilisateur_recette_favoris",
            joinColumns = @JoinColumn(name = "utilisateur_id"),
            inverseJoinColumns = @JoinColumn(name = "recette_id"))
    private List<Recette> recettesFavoris = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_id")
    private Stock stock;
    public Utilisateur() {

    }

    public Utilisateur(Long id, String prénom, String nom, String email, String password, String régimeSpécieux, boolean modeSportif) {
        this.id = id;
        this.prénom = prénom;
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.régimeSpécieux = régimeSpécieux;
        this.modeSportif = modeSportif;
    }


}

