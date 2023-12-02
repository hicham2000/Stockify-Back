package com.example.stockifybackend.Entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Utilisateur implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String prénom;
    private String nom;
    private String email;
    private String password;
    private boolean modeSportif;
    @OneToMany
    @JoinColumn(name = "id")
    private List<PréférenceAlimentaire> préférenceAlimentaires = new ArrayList<>();
}
