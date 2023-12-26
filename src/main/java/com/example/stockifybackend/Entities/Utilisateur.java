package com.example.stockifybackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Utilisateur implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String prénom;
    private String nom;
    private String email;
    private String password;
    private String sexe;
    private String taille;
    private String poids;
    private Date dateDeNaissance;
    private String régimeSpécieux;
    private boolean modeSportif;
    @OneToMany
    @JoinColumn(name = "id")
    private List<PréférenceAlimentaire> préférenceAlimentaires = new ArrayList<>();
    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<Stock> stocks = new ArrayList<>();
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
