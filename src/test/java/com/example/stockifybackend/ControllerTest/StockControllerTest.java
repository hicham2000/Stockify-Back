package com.example.stockifybackend.ControllerTest;

import com.example.stockifybackend.Controllers.StockController;
import com.example.stockifybackend.Entities.Stock;
import com.example.stockifybackend.Repositories.StockRepository;
import com.example.stockifybackend.services.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class StockControllerTest {

    @Mock
    private StockService stockService;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockController stockController;

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
        when(stockRepository.findById(anyLong())).thenReturn(Optional.empty());
    }

    // ... (previous tests)

    @Test
    void testGetStockByIdWhenStockExists() {
        Stock expectedStock = new Stock();
        when(stockRepository.findById(anyLong())).thenReturn(Optional.of(expectedStock));

        Stock result = stockController.getStockById(1L);

        assertEquals(expectedStock, result);
        verify(stockRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(stockService);
    }

    @Test
    void testGetStockByIdWhenStockDoesNotExist() {
        Stock result = stockController.getStockById(1L);

        assertEquals(null, result);
        verify(stockRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(stockService);
    }
}


