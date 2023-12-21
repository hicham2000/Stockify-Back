package com.example.stockifybackend.RepositoryTest;

import com.example.stockifybackend.Entities.Ingredient;
import com.example.stockifybackend.Entities.Recette;
import com.example.stockifybackend.Entities.Recommendation;
import com.example.stockifybackend.Entities.Stock;
import com.example.stockifybackend.Repositories.RecetteRepository;
import com.example.stockifybackend.Repositories.RecommendationRepository;
import com.example.stockifybackend.Repositories.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RecetteRepositoryTest {

    private final RecetteRepository recetteRepository;
    private final StockRepository stockRepository;
    private final RecommendationRepository recommendationRepository;

    @Autowired
    RecetteRepositoryTest(RecetteRepository recetteRepository, StockRepository stockRepository, RecommendationRepository recommendationRepository) {
        this.recetteRepository = recetteRepository;
        this.stockRepository = stockRepository;
        this.recommendationRepository = recommendationRepository;
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
    @Transactional
    void testFindAllByStockIdCustomQuery() {
        // Save test data to the database
        Stock stock = new Stock();
        Recommendation recommendation = new Recommendation();

        stock = stockRepository.save(stock); // Save the Stock entity and update the reference
        recommendation = recommendationRepository.save(recommendation);

        // Créer des données de test
        Recette recette1 = new Recette("Recette 1", "Description 1", 60, stock, Arrays.asList(new Ingredient()), recommendation, 0);
        Recette recette2 = new Recette("Recette 2", "Description 2", 90, stock, Arrays.asList(new Ingredient()), recommendation, 0);

        // Enregistrer les données dans la base de données
        recetteRepository.save(recette1);
        recetteRepository.save(recette2);

        // Appeler la méthode du repository
        List<Recette> recettes = recetteRepository.findAllByStockIdCustomQuery(stock.getId());

        // Vérifier les assertions
        assertThat(recettes).isNotNull();
        assertThat(recettes.size()).isEqualTo(2);
        assertThat(recettes.get(0).getIntitule()).isEqualTo("Recette 1");
        assertThat(recettes.get(1).getIntitule()).isEqualTo("Recette 2");
    }
}
