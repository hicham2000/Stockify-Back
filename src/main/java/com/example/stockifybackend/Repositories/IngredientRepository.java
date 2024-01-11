package com.example.stockifybackend.Repositories;

import com.example.stockifybackend.Entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    @Query("SELECT i FROM Ingredient i")
    List<Ingredient> findIngredientRandomly(org.springframework.data.domain.Pageable pageable);
}
