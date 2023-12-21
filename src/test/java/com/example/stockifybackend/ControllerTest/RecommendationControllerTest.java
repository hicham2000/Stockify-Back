package com.example.stockifybackend.ControllerTest;

import com.example.stockifybackend.Controllers.RecommendationController;
import com.example.stockifybackend.Entities.Recommendation;
import com.example.stockifybackend.services.RecommendationService;
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
class RecommendationControllerTest {

    @Mock
    private RecommendationService recommendationService;

    @InjectMocks
    private RecommendationController recommendationController;

    @Test
    void testGetAllRecommendations() {
        // Arrange
        List<Recommendation> mockRecommendations = new ArrayList<>();
        when(recommendationService.getAllRecommendations()).thenReturn(mockRecommendations);

        // Act
        ResponseEntity<List<Recommendation>> result = recommendationController.getAllRecommendations();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockRecommendations, result.getBody());
    }

    @Test
    void testGetRecommendationById() {
        // Arrange
        Long recommendationId = 1L;
        Recommendation mockRecommendation = new Recommendation();
        when(recommendationService.getRecommendationById(recommendationId)).thenReturn(Optional.of(mockRecommendation));

        // Act
        ResponseEntity<Recommendation> result = recommendationController.getRecommendationById(recommendationId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockRecommendation, result.getBody());
    }

    @Test
    void testGetRecommendationById_NotFound() {
        // Arrange
        Long recommendationId = 1L;
        when(recommendationService.getRecommendationById(recommendationId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Recommendation> result = recommendationController.getRecommendationById(recommendationId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    // Add similar tests for other controller methods: addRecommendation, updateRecommendation, deleteRecommendation
    // ...

}

