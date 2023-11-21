package com.example.stockifybackend.Repositories;

import com.example.stockifybackend.Entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
}
