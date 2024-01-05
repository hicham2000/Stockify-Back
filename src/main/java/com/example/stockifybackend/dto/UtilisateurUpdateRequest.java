package com.example.stockifybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
@AllArgsConstructor
@Data
@Builder
public class UtilisateurUpdateRequest {
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
}
