package com.example.stockifybackend.Repositories;

import com.example.stockifybackend.Entities.CategorieDeProduits;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieDeProduitsRepository extends JpaRepository<CategorieDeProduits,Long> {
}
