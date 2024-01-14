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
    @Query("SELECT r FROM Repas r WHERE r.stock.id = :stockId AND r.is_deleted = 1 AND r.permanent = 0")
    List<Repas> findAllDeletedRecipesInStock(Long stockId);
    @Transactional
    @Modifying
    @Query("DELETE FROM Repas r WHERE r.stock.id = :stockId AND r.is_deleted = 1")
    void deleteAllDeletedRecipesInStock(Long stockId);
    @Transactional
    @Modifying
    @Query("UPDATE Repas r SET r.permanent = 1 WHERE r.stock.id = :stockId AND r.is_deleted = 1")
    void updatePermanentFlagForDeletedRecipesInStock(Long stockId);
    @Query("select r from Repas r where r.stock.id = :stockId and r.is_deleted = 0 and r.permanent = 0")
    List<Repas> repas(Long stockId);
}
