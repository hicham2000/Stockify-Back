package com.example.stockifybackend.ControllerTest;

import com.example.stockifybackend.Controllers.DepenseController;
import com.example.stockifybackend.Entities.Depense;
import com.example.stockifybackend.services.DepenseService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class DepenseControllerTest {

    @Mock
    private DepenseService depenseService;

    @InjectMocks
    private DepenseController depenseController;

    @Test
    void testGetAllDepenses() {
        // Arrange
        List<Depense> mockDepenses = new ArrayList<>();
        when(depenseService.getAllDepenses()).thenReturn(mockDepenses);

        // Act
        ResponseEntity<List<Depense>> result = depenseController.getAllDepenses();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockDepenses, result.getBody());
    }

    @Test
    void testGetDepenseById() {
        // Arrange
        Long depenseId = 1L;
        Depense mockDepense = new Depense();
        when(depenseService.getDepenseById(depenseId)).thenReturn(Optional.of(mockDepense));

        // Act
        ResponseEntity<Depense> result = depenseController.getDepenseById(depenseId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockDepense, result.getBody());
    }

    @Test
    void testGetDepenseById_NotFound() {
        // Arrange
        Long depenseId = 1L;
        when(depenseService.getDepenseById(depenseId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Depense> result = depenseController.getDepenseById(depenseId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertTrue(result.getBody() == null);
    }

    // Add similar tests for other controller methods: addDepense, updateDepense, deleteDepense
    // ...

}
