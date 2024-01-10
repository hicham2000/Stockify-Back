package com.example.stockifybackend.Repositories;

import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.Entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    @Query("SELECT p FROM Produit p WHERE p.stock.id = :stockId")
    List<Produit> findAllByStockIdCustomQuery(Long stockId);

    @Query("SELECT p FROM Produit p WHERE p.id_produitCourse = :id_produitcourse")
    Optional<Produit> findById_produitCourse(Long id_produitcourse);


}
