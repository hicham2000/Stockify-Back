package com.example.stockifybackend.Entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class PréférenceAlimentaire implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String intitule;
    private String description;
    @ElementCollection
    private List<String> produits;

}
