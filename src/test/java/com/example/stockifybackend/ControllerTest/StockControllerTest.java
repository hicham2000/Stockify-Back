package com.example.stockifybackend.ControllerTest;

import com.example.stockifybackend.Entities.Recette;
import com.example.stockifybackend.Entities.Stock;
import com.example.stockifybackend.services.StockService;
import com.example.stockifybackend.Repositories.StockRepository;
import com.example.stockifybackend.Controllers.StockController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StockControllerTest {

    @Mock
    private StockService stockService;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockController stockController;

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

    @Test
    void testAddRecipeToStockByRecetteID_Success() {
        // Arrange
        Long stockId = 1L;
        Long recetteId = 38L;

        // Act
        ResponseEntity<?> responseEntity = stockController.addRecipeToStockByRecetteID(stockId, recetteId);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Map);

        Map<?, ?> responseBody = (Map<?, ?>) responseEntity.getBody();
        assertEquals("Recette ajoutée au stock avec succès", responseBody.get("message"));
        verify(stockService, times(1)).addRecipeToStockByRecetteId(stockId, recetteId);
    }

    @Test
    void testAddRecipeToStockByRecetteID_Failure() {
        // Arrange
        Long stockId = 1L;
        Long recetteId = 38L;
        doThrow(new RuntimeException("Some error message")).when(stockService).addRecipeToStockByRecetteId(stockId, recetteId);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> stockController.addRecipeToStockByRecetteID(stockId, recetteId));
        verify(stockService, times(1)).addRecipeToStockByRecetteId(stockId, recetteId);
    }

    @Test
    void testDeleteRecipeFromStock() {
        // Arrange
        Long stockId = 1L;
        Long recipeId = 38L;

        // Act
        ResponseEntity<Void> responseEntity = stockController.deleteRecipeFromStock(stockId, recipeId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(stockService, times(1)).deleteRecipeFromStock(stockId, recipeId);
    }
}


