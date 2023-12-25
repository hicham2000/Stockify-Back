package com.example.stockifybackend.Repositories;

import com.example.stockifybackend.Entities.Repas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepasRepository extends JpaRepository<Repas,Long> {
}
