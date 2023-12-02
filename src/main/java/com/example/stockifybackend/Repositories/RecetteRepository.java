package com.example.stockifybackend.Repositories;


import com.example.stockifybackend.Entities.Recette;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetteRepository extends JpaRepository<Recette, Long> {
}
