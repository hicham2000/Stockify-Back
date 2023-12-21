package com.example.stockifybackend.ControllerTest;

import com.example.stockifybackend.Controllers.IntoleranceController;
import com.example.stockifybackend.Entities.Intolerance;
import com.example.stockifybackend.services.IntoleranceService;
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
class IntoleranceControllerTest {

    @Mock
    private IntoleranceService intoleranceService;

    @InjectMocks
    private IntoleranceController intoleranceController;

    @Test
    void testGetAllIntolerances() {
        // Arrange
        List<Intolerance> mockIntolerances = new ArrayList<>();
        when(intoleranceService.getAllIntolerances()).thenReturn(mockIntolerances);

        // Act
        ResponseEntity<List<Intolerance>> result = intoleranceController.getAllIntolerances();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockIntolerances, result.getBody());
    }

    @Test
    void testGetIntoleranceById() {
        // Arrange
        Long intoleranceId = 1L;
        Intolerance mockIntolerance = new Intolerance();
        when(intoleranceService.getIntoleranceById(intoleranceId)).thenReturn(Optional.of(mockIntolerance));

        // Act
        ResponseEntity<Intolerance> result = intoleranceController.getIntoleranceById(intoleranceId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockIntolerance, result.getBody());
    }

    @Test
    void testGetIntoleranceById_NotFound() {
        // Arrange
        Long intoleranceId = 1L;
        when(intoleranceService.getIntoleranceById(intoleranceId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Intolerance> result = intoleranceController.getIntoleranceById(intoleranceId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertTrue(result.getBody() == null);
    }

    // Add similar tests for other controller methods: addIntolerance, updateIntolerance, deleteIntolerance
    // ...

}

