package com.example.stockifybackend.RepositoryTest;

import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.Entities.Stock;
import com.example.stockifybackend.Repositories.ProduitRepository;
import com.example.stockifybackend.Repositories.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProduitRepositoryTest {

    private final ProduitRepository produitRepository;
    private final StockRepository stockRepository; // Add StockRepository

    @Autowired
    ProduitRepositoryTest(ProduitRepository produitRepository, StockRepository stockRepository) {
        this.produitRepository = produitRepository;
        this.stockRepository = stockRepository;
    }

    @BeforeEach
    void setUp() {
        // You can initialize or pre-populate the database with test data here if needed
    }

    @AfterEach
    void tearDown() {
        // Cleanup or reset the database state after each test if needed
    }

    @Test
    void testFindAllByStockIdCustomQuery() {
        // Save test data to the database
        Stock stock = new Stock();

        stock = stockRepository.save(stock); // Save the Stock entity and update the reference

        Produit produit1 = new Produit("Product 1", "Description 1", "Brand 1", "Unit 1", new Date(),
                10.0, 20.0, 5.0, null, null, stock, null);

        Produit produit2 = new Produit("Product 2", "Description 2", "Brand 2", "Unit 2", new Date(),
                15.0, 25.0, 8.0, null, null, stock, null);

        produitRepository.save(produit1);
        produitRepository.save(produit2);

        // Retrieve products by stock ID using the custom query
        List<Produit> produits = produitRepository.findAllByStockIdCustomQuery(stock.getId());

        // Assertions
        assertThat(produits).isNotNull();
        assertThat(produits.size()).isEqualTo(2);
        assertThat(produits.get(0).getIntitule()).isEqualTo("Product 1");
        assertThat(produits.get(1).getIntitule()).isEqualTo("Product 2");

    }
}
