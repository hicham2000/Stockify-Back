package com.example.stockifybackend.ControllerTest;

import org.springframework.boot.test.context.SpringBootTest;
import com.example.stockifybackend.Entities.Ingredient;
import com.example.stockifybackend.Repositories.IngredientRepository;
import com.example.stockifybackend.Controllers.IngredientController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientController ingredientController;

    @Test
    void testGetIngredientByID_IngredientExists() {
        // Arrange
        Long ingredientId = 1L;
        Ingredient mockIngredient = new Ingredient();
        mockIngredient.setId(ingredientId);
        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(mockIngredient));

        // Act
        ResponseEntity<?> responseEntity = ingredientController.getIngredientByID(ingredientId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Map);

        Map<?, ?> responseBody = (Map<?, ?>) responseEntity.getBody();
        assertEquals("Ingredient récupéré par succès", responseBody.get("message"));
        assertTrue(responseBody.containsKey("ingredient"));
        assertEquals(mockIngredient, responseBody.get("ingredient"));
    }

    @Test
    void testGetIngredientByID_IngredientDoesNotExist() {
        // Arrange
        Long ingredientId = 1L;
        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> responseEntity = ingredientController.getIngredientByID(ingredientId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Map);

        Map<?, ?> responseBody = (Map<?, ?>) responseEntity.getBody();
        assertEquals("Aucun Ingredient avec id=" + ingredientId, responseBody.get("message"));
        assertFalse(responseBody.containsKey("ingredient"));
    }

}
