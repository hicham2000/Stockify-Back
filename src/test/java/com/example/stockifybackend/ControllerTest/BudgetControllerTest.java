package com.example.stockifybackend.ControllerTest;

import com.example.stockifybackend.Controllers.BudgetController;
import com.example.stockifybackend.Entities.Budget;
import com.example.stockifybackend.services.BudgetService;
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
class BudgetControllerTest {

    @Mock
    private BudgetService budgetService;

    @InjectMocks
    private BudgetController budgetController;

    @Test
    void testGetAllBudgets() {
        // Arrange
        List<Budget> mockBudgets = new ArrayList<>();
        when(budgetService.getAllBudgets()).thenReturn(mockBudgets);

        // Act
        ResponseEntity<List<Budget>> result = budgetController.getAllBudgets();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockBudgets, result.getBody());
    }

    @Test
    void testGetBudgetById() {
        // Arrange
        Long budgetId = 1L;
        Budget mockBudget = new Budget();
        when(budgetService.getBudgetById(budgetId)).thenReturn(Optional.of(mockBudget));

        // Act
        ResponseEntity<Budget> result = budgetController.getBudgetById(budgetId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockBudget, result.getBody());
    }

    @Test
    void testGetBudgetById_NotFound() {
        // Arrange
        Long budgetId = 1L;
        when(budgetService.getBudgetById(budgetId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Budget> result = budgetController.getBudgetById(budgetId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    // Add similar tests for other controller methods: addBudget, updateBudget, deleteBudget
    // ...

}
