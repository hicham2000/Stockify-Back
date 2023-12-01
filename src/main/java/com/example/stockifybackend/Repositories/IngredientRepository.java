package com.example.stockifybackend.Repositories;

import com.example.stockifybackend.Entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
