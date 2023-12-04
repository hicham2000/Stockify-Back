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

    public Utilisateur() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrénom() {
        return prénom;
    }

    public void setPrénom(String prénom) {
        this.prénom = prénom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isModeSportif() {
        return modeSportif;
    }

    public void setModeSportif(boolean modeSportif) {
        this.modeSportif = modeSportif;
    }

    public List<PréférenceAlimentaire> getPréférenceAlimentaires() {
        return préférenceAlimentaires;
    }

    public void setPréférenceAlimentaires(List<PréférenceAlimentaire> préférenceAlimentaires) {
        this.préférenceAlimentaires = préférenceAlimentaires;
    }
}
