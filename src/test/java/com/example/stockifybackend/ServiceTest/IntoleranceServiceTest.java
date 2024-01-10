package com.example.stockifybackend.ServiceTest;

import com.example.stockifybackend.Entities.Intolerance;
import com.example.stockifybackend.Repositories.IntoleranceRepository;
import com.example.stockifybackend.services.IntoleranceService;
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

public class IntoleranceServiceTest {

    @Mock
    private IntoleranceRepository intoleranceRepository;

    @InjectMocks
    private IntoleranceService intoleranceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllIntolerances() {
        // Arrange
        List<Intolerance> intoleranceList = new ArrayList<>();
        when(intoleranceRepository.findAll()).thenReturn(intoleranceList);

        // Act
        List<Intolerance> result = intoleranceService.getAllIntolerances();

        // Assert
        assertEquals(intoleranceList, result);
        verify(intoleranceRepository, times(1)).findAll();
    }

    @Test
    public void testGetIntoleranceById() {
        // Arrange
        Long intoleranceId = 1L;
        Intolerance intolerance = new Intolerance();
        when(intoleranceRepository.findById(intoleranceId)).thenReturn(Optional.of(intolerance));

        // Act
        Optional<Intolerance> result = intoleranceService.getIntoleranceById(intoleranceId);

        // Assert
        assertEquals(Optional.of(intolerance), result);
        verify(intoleranceRepository, times(1)).findById(intoleranceId);
    }

    @Test
    public void testSaveIntolerance() {
        // Arrange
        Intolerance intolerance = new Intolerance();
        when(intoleranceRepository.save(intolerance)).thenReturn(intolerance);

        // Act
        Intolerance result = intoleranceService.saveIntolerance(intolerance);

        // Assert
        assertEquals(intolerance, result);
        verify(intoleranceRepository, times(1)).save(intolerance);
    }

    @Test
    public void testDeleteIntolerance() {
        // Arrange
        Long intoleranceId = 1L;

        // Act
        intoleranceService.deleteIntolerance(intoleranceId);

        // Assert
        verify(intoleranceRepository, times(1)).deleteById(intoleranceId);
    }
}

