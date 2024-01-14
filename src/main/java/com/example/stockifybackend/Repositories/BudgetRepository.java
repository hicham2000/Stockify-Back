package com.example.stockifybackend.Repositories;


import com.example.stockifybackend.Entities.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    @Query("SELECT SUM(p.prix) AS total_price FROM Produit p WHERE p.stock.id = :stockId AND p.gaspille=1")
    Double PriceGaspilleProduits(Long stockId);

    @Query("SELECT SUM(p.prix) AS total_price FROM Produit p WHERE p.stock.id = :stockId AND p.gaspille=0")
    Double PriceNonGaspilleProduits(Long stockId);
}
