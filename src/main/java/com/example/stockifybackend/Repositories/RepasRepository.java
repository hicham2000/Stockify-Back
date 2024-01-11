package com.example.stockifybackend.Repositories;

import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.Entities.Recette;
import com.example.stockifybackend.Entities.Repas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RepasRepository extends JpaRepository<Repas,Long> {
    @Query("SELECT r FROM Repas r WHERE r.stock.id = :stockId")
    List<Repas> findAllRepasByStockIdCustomQuery(Long stockId);
    @Query("SELECT r FROM Repas r WHERE r.stock.id = :stockId AND r.is_deleted = 1 AND r.permanent = 0")
    List<Repas> findAllDeletedRecipesInStock(Long stockId);
    @Transactional
    @Modifying
    @Query("DELETE FROM Repas r WHERE r.stock.id = :stockId AND r.is_deleted = 1")
    void deleteAllDeletedRecipesInStock(Long stockId);
}
