package com.example.stockifybackend.Entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategorieDeRecette {
    private long id;
    private String intitule;

    public CategorieDeRecette(String intitule) {
        this.intitule = intitule;
    }
}
