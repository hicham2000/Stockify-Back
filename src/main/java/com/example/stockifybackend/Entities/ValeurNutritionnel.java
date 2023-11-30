package com.example.stockifybackend.Entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;



@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ValeurNutritionnel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long id;
    private Number proteine;
    private Number carbohydrate;
    private Number lipide;
    private Number enegie;
    private Number sucre;
    private Number fibre;

}


