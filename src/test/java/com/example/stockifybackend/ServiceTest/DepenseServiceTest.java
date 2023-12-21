package com.example.stockifybackend.ServiceTest;

import com.example.stockifybackend.Entities.Depense;
import com.example.stockifybackend.Repositories.DepenseRepository;
import com.example.stockifybackend.services.DepenseService;
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

public class DepenseServiceTest {

    @Mock
    private DepenseRepository depenseRepository;

    @InjectMocks
    private DepenseService depenseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllDepenses() {
        // Arrange
        List<Depense> depenseList = new ArrayList<>();
        when(depenseRepository.findAll()).thenReturn(depenseList);

        // Act
        List<Depense> result = depenseService.getAllDepenses();

        // Assert
        assertEquals(depenseList, result);
        verify(depenseRepository, times(1)).findAll();
    }

    @Test
    public void testGetDepenseById() {
        // Arrange
        Long depenseId = 1L;
        Depense depense = new Depense();
        when(depenseRepository.findById(depenseId)).thenReturn(Optional.of(depense));

        // Act
        Optional<Depense> result = depenseService.getDepenseById(depenseId);

        // Assert
        assertEquals(Optional.of(depense), result);
        verify(depenseRepository, times(1)).findById(depenseId);
    }

    @Test
    public void testSaveDepense() {
        // Arrange
        Depense depense = new Depense();
        when(depenseRepository.save(depense)).thenReturn(depense);

        // Act
        Depense result = depenseService.saveDepense(depense);

        // Assert
        assertEquals(depense, result);
        verify(depenseRepository, times(1)).save(depense);
    }

    @Test
    public void testDeleteDepense() {
        // Arrange
        Long depenseId = 1L;

        // Act
        depenseService.deleteDepense(depenseId);

        // Assert
        verify(depenseRepository, times(1)).deleteById(depenseId);
    }
}

