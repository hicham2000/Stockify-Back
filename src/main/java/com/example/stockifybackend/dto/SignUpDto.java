package com.example.stockifybackend.dto;

public class SignUpDto {
    private String prénom;
    private String nom;
    private String email;
    private String password;
    private String régimeSpécieux;

    public SignUpDto() {
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

    public String getRégimeSpécieux() {
        return régimeSpécieux;
    }

    public void setRégimeSpécieux(String régimeSpécieux) {
        this.régimeSpécieux = régimeSpécieux;
    }
}
