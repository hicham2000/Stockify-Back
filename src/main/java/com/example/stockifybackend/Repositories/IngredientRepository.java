package com.example.stockifybackend.Repositories;

import com.example.stockifybackend.Entities.Ingredient;
import com.example.stockifybackend.Entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    @Query("SELECT i FROM Ingredient i")
    List<Ingredient> findIngredientRandomly(org.springframework.data.domain.Pageable pageable);

    @Query("SELECT i FROM Ingredient i WHERE i.repas.id = :id")
    List<Ingredient> findAllByrepasid(Long id);
}
