package com.example.stockifybackend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
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
    private String régimeSpécieux;
    private boolean modeSportif;
    @OneToMany
    @JoinColumn(name = "id")
    private List<PréférenceAlimentaire> préférenceAlimentaires = new ArrayList<>();

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
