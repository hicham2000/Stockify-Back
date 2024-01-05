package com.example.stockifybackend.Repositories;


import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.Entities.Recette;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RecetteRepository extends PagingAndSortingRepository<Recette, Long> {
    @Query("SELECT r FROM Recette r WHERE r.stock.id = :stockId")
    List<Recette> findAllByStockIdCustomQuery(Long stockId);
}
