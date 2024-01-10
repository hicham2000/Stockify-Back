package com.example.stockifybackend.ServiceTest;

import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.Entities.Recette;
import com.example.stockifybackend.Entities.Stock;
import com.example.stockifybackend.Repositories.ProduitRepository;
import com.example.stockifybackend.Repositories.RecetteRepository;
import com.example.stockifybackend.Repositories.StockRepository;
import com.example.stockifybackend.services.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private RecetteRepository recetteRepository;

    @InjectMocks
    private StockService stockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddProductToStock() {
        Long stockId = 1L;
        Produit produit = new Produit();
        Stock stock = new Stock();

        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stock));

        stockService.addProductToStock(stockId, produit);

        verify(produitRepository).save(produit);
        verify(stockRepository).save(stock);

        assertEquals(stock, produit.getStock());
        assertTrue(stock.getProduit().contains(produit));
    }

    @Test
    void testDeleteProductFromStock() {
        Long stockId = 1L;
        Long produitId = 2L;
        Produit produit = new Produit();
        produit.setId(produitId);
        Stock stock = new Stock();
        stock.getProduit().add(produit);

        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stock));

        stockService.deleteProductFromStock(stockId, produitId);

        verify(produitRepository).deleteById(produitId);
        verify(stockRepository).save(stock);

        assertFalse(stock.getProduit().contains(produit));
    }

}
