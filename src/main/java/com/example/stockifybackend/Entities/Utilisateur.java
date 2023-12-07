package com.example.stockifybackend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @ManyToMany
    private Set<Role> roles;
    public Utilisateur() {

    }

}
