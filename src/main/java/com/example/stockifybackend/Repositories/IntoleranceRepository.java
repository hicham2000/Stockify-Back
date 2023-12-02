package com.example.stockifybackend.Repositories;


import com.example.stockifybackend.Entities.Intolerance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntoleranceRepository extends JpaRepository<Intolerance, Long> {
}
