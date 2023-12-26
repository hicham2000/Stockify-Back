package com.example.stockifybackend.Repositories;


import com.example.stockifybackend.Entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {

   // @Query("SELECT DISTINCT s FROM Stock s JOIN s.produit p WHERE p.expiryDate <= :expiryDate")
     //List<Stock> findStocksWithExpiringProducts(@Param("expiryDate") LocalDate expiryDate) {


}
