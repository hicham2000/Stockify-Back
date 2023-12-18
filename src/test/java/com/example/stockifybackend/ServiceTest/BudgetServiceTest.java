package com.example.stockifybackend.ServiceTest;

import com.example.stockifybackend.Entities.Budget;
import com.example.stockifybackend.Repositories.BudgetRepository;
import com.example.stockifybackend.services.BudgetService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
@Transactional
class BudgetServiceTest {

    @Mock
    private BudgetRepository budgetRepository;

    @InjectMocks
    private BudgetService budgetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBudgets() {
        // Mock data
        List<Budget> mockBudgets = Arrays.asList(
                new Budget(1L, 1000.0, 500.0, 200.0, "Monthly", null),
                new Budget(2L, 2000.0, 1000.0, 500.0, "Monthly", null)
        );

        // Mock repository behavior
        when(budgetRepository.findAll()).thenReturn(mockBudgets);

        // Call the service method
        List<Budget> result = budgetService.getAllBudgets();

        // Verify the result
        assertEquals(2, result.size());
        assertEquals(1000.0, result.get(0).getBudgetTotal());
        assertEquals(2000.0, result.get(1).getBudgetTotal());

        // Verify that the repository method was called
        verify(budgetRepository, times(1)).findAll();
    }

    @Test
    void getBudgetById() {
        // Mock data
        Budget mockBudget = new Budget(1L, 1000.0, 500.0, 200.0, "Monthly", null);

        // Mock repository behavior
        when(budgetRepository.findById(1L)).thenReturn(Optional.of(mockBudget));

        // Call the service method
        Optional<Budget> result = budgetService.getBudgetById(1L);

        // Verify the result
        assertTrue(result.isPresent());
        assertEquals(1000.0, result.get().getBudgetTotal());

        // Verify that the repository method was called
        verify(budgetRepository, times(1)).findById(1L);
    }

    @Test
    void saveBudget() {
        // Mock data
        Budget mockBudget = new Budget(null, 1000.0, 500.0, 200.0, "Monthly", null);

        // Mock repository behavior
        when(budgetRepository.save(mockBudget)).thenReturn(mockBudget);

        // Call the service method
        Budget result = budgetService.saveBudget(mockBudget);

        // Verify the result
        assertEquals(1000.0, result.getBudgetTotal());

        // Verify that the repository method was called
        verify(budgetRepository, times(1)).save(mockBudget);
    }

    @Test
    void deleteBudget() {
        // Call the service method
        budgetService.deleteBudget(1L);

        // Verify that the repository method was called
        verify(budgetRepository, times(1)).deleteById(1L);
    }
}
