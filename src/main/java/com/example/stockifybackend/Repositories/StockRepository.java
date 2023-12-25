package com.example.stockifybackend.Repositories;


import com.example.stockifybackend.Entities.Repas;
import com.example.stockifybackend.Entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("SELECT s FROM Stock s WHERE s.id = :stockId")
    List<Repas> selectStock(Long stockId);

}
