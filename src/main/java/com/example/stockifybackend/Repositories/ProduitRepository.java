package com.example.stockifybackend.Repositories;

import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.Entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    @Query("SELECT p FROM Produit p WHERE p.stock.id = :stockId and p.is_deleted = 0")
    List<Produit> findAllByStockIdCustomQuery(Long stockId);


    @Query("SELECT p FROM Produit p WHERE p.stock.id = :stockId and p.gaspille = 1")
    List<Produit> findAllByStockIdCustomQueryGaspille(Long stockId);

    @Query("SELECT p FROM Produit p WHERE p.id_produitCourse = :id_produitcourse")
    Optional<Produit> findById_produitCourse(Long id_produitcourse);


    @Query("SELECT p FROM Produit p WHERE p.stock.id = :stockId AND p.is_deleted = 1 AND p.permanent =0")
    List<Produit> findAllDeletedProductsInStock(Long stockId);
    @Transactional
    @Modifying
    @Query("DELETE FROM Produit p WHERE p.stock.id = :stockId AND p.is_deleted = 1")
    void deleteAllDeletedProductsInStock(Long stockId);
    @Transactional
    @Modifying
    @Query("UPDATE Produit p SET p.permanent = 1 WHERE p.stock.id = :stockId AND p.is_deleted = 1")
    void updatePermanentFlagForDeletedProductsInStock(Long stockId);

    @Query("select p from Produit p where p.is_deleted = 0 and p.stock.id = :stockId ")
    List<Produit> produits(Long stockId);

    @Transactional
    @Modifying
    @Query("UPDATE Produit p SET p.is_deleted = 1 WHERE p.stock.id = :stockId AND p.id=:produitId")
    void updateDeleteProductsInStock(Long stockId,Long produitId);


}
