package com.example.stockifybackend.Repositories;

import com.example.stockifybackend.Entities.Recette;
import com.example.stockifybackend.Entities.Repas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepasRepository extends JpaRepository<Repas,Long> {
    @Query("SELECT r FROM Repas r WHERE r.stock.id = :stockId")
    List<Repas> findAllRepasByStockIdCustomQuery(Long stockId);
}
