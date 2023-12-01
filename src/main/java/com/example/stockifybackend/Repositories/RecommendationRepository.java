package com.example.stockifybackend.Repositories;


import com.example.stockifybackend.Entities.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
}
