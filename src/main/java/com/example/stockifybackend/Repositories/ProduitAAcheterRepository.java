package com.example.stockifybackend.Repositories;


import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.Entities.ProduitAAcheter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProduitAAcheterRepository extends JpaRepository<ProduitAAcheter, Long> {
    @Query("SELECT product FROM ProduitAAcheter product WHERE product.listeCourse.id = :courseId ")
    List<ProduitAAcheter> findAllByListeCourseIdCustomQuery(Long courseId);
}
