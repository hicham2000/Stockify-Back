package com.example.stockifybackend.ServiceTest;

import com.example.stockifybackend.Entities.Recommendation;
import com.example.stockifybackend.Repositories.RecommendationRepository;
import com.example.stockifybackend.services.RecommendationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RecommendationServiceTest {

    @Mock
    private RecommendationRepository recommendationRepository;

    @InjectMocks
    private RecommendationService recommendationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllRecommendations() {
        // Arrange
        List<Recommendation> recommendationList = new ArrayList<>();
        when(recommendationRepository.findAll()).thenReturn(recommendationList);

        // Act
        List<Recommendation> result = recommendationService.getAllRecommendations();

        // Assert
        assertEquals(recommendationList, result);
        verify(recommendationRepository, times(1)).findAll();
    }

    @Test
    public void testGetRecommendationById() {
        // Arrange
        Long recommendationId = 1L;
        Recommendation recommendation = new Recommendation();
        when(recommendationRepository.findById(recommendationId)).thenReturn(Optional.of(recommendation));

        // Act
        Optional<Recommendation> result = recommendationService.getRecommendationById(recommendationId);

        // Assert
        assertEquals(Optional.of(recommendation), result);
        verify(recommendationRepository, times(1)).findById(recommendationId);
    }

    @Test
    public void testSaveRecommendation() {
        // Arrange
        Recommendation recommendation = new Recommendation();
        when(recommendationRepository.save(recommendation)).thenReturn(recommendation);

        // Act
        Recommendation result = recommendationService.saveRecommendation(recommendation);

        // Assert
        assertEquals(recommendation, result);
        verify(recommendationRepository, times(1)).save(recommendation);
    }

    @Test
    public void testDeleteRecommendation() {
        // Arrange
        Long recommendationId = 1L;

        // Act
        recommendationService.deleteRecommendation(recommendationId);

        // Assert
        verify(recommendationRepository, times(1)).deleteById(recommendationId);
    }
}

