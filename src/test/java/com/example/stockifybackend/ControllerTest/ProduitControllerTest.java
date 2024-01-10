package com.example.stockifybackend.ControllerTest;

import com.example.stockifybackend.Controllers.ProduitController;
import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.Repositories.ProduitRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProduitControllerTest {

    @Mock
    private ProduitRepository produitRepository;

    @InjectMocks
    private ProduitController produitController;

    @Test
    void testGetReservations() {
        // Arrange
        Produit produit1 = new Produit();
        produit1.setPrix(0);

        Produit produit2 = new Produit();
        produit2.setPrix(5);

        List<Produit> mockProduits = new ArrayList<>();
        mockProduits.add(produit1);
        mockProduits.add(produit2);

        when(produitRepository.findAll()).thenReturn(mockProduits);

        // Act
        List<Produit> result = produitController.getReservations();

        // Assert
        List<Produit> expectedProduits = new ArrayList<>();
        expectedProduits.add(produit1);

        assertEquals(expectedProduits, result);
    }
}
