package com.example.stockifybackend.Repositories;


import com.example.stockifybackend.Entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
