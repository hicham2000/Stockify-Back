package com.example.stockifybackend.RepositoryTest;

import com.example.stockifybackend.Entities.ListeCourse;
import com.example.stockifybackend.Entities.ProduitAAcheter;
import com.example.stockifybackend.Repositories.ProduitAAcheterRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProduitAAcheterRepositoryTest {

    @Autowired
    private ProduitAAcheterRepository produitAAcheterRepository;

    @BeforeEach
    void setUp() {
        // You can initialize or pre-populate the database with test data here if needed
    }

    @AfterEach
    void tearDown() {
        // Cleanup or reset the database state after each test if needed
    }

    @Test
    void testFindAllByListeCourseIdCustomQuery() {
        // Save test data to the database
        ListeCourse listeCourse = new ListeCourse();
        ProduitAAcheter produitAAcheter1 = new ProduitAAcheter("Product 1", "Unit 1", 5, false);
        ProduitAAcheter produitAAcheter2 = new ProduitAAcheter("Product 2", "Unit 2", 10, true);

        produitAAcheterRepository.save(produitAAcheter1);
        produitAAcheterRepository.save(produitAAcheter2);

        // Retrieve products to be bought by course ID using the custom query
        List<ProduitAAcheter> produitsAAcheter = produitAAcheterRepository.findAllByListeCourseIdCustomQuery(listeCourse.getId());

        // Assertions
        assertThat(produitsAAcheter).isNotNull();
        assertThat(produitsAAcheter.size()).isEqualTo(2);
        assertThat(produitsAAcheter.get(0).getQuantite()).isEqualTo(5);
        assertThat(produitsAAcheter.get(1).getQuantite()).isEqualTo(10);
    }
}
