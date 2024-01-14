package com.example.stockifybackend.RepositoryTest;

import com.example.stockifybackend.Entities.Budget;
import com.example.stockifybackend.Repositories.BudgetRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@Transactional
@SpringBootTest
class BudgetRepositoryTest {

    @Autowired
    private BudgetRepository budgetRepository;

    @BeforeEach
    void setUp() {
        // You can initialize or pre-populate the database with test data here if needed
    }

    @AfterEach
    void tearDown() {
        // Cleanup or reset the database state after each test if needed
    }

    @Test
    void testFindAll() {
        // Save test data to the database
        budgetRepository.save(new Budget(null, 1000.0, 500.0, 200.0, "Monthly", null,null));
        budgetRepository.save(new Budget(null, 2000.0, 1000.0, 500.0, "Monthly", null,null));

        // Retrieve all budgets from the repository
        List<Budget> budgets = budgetRepository.findAll();

        // Assertions
        assertThat(budgets).isNotNull();
        assertThat(budgets.size()).isEqualTo(2);
        assertThat(budgets.get(0).getBudgetTotal()).isEqualTo(1000.0);
        assertThat(budgets.get(1).getBudgetTotal()).isEqualTo(2000.0);
    }

    @Test
    void testFindById() {
        // Save a budget to the database
        Budget savedBudget = budgetRepository.save(new Budget(null, 1000.0, 500.0, 200.0, "Monthly", null,null));

        // Retrieve the budget by ID from the repository
        Optional<Budget> retrievedBudget = budgetRepository.findById(savedBudget.getId());

        // Assertions
        assertThat(retrievedBudget).isPresent();
        assertThat(retrievedBudget.get().getBudgetTotal()).isEqualTo(1000.0);
    }

    @Test
    void testSave() {
        // Save a budget to the database
        Budget savedBudget = budgetRepository.save(new Budget(null, 1000.0, 500.0, 200.0, "Monthly", null,null));

        // Retrieve the budget by ID from the repository
        Optional<Budget> retrievedBudget = budgetRepository.findById(savedBudget.getId());

        // Assertions
        assertThat(retrievedBudget).isPresent();
        assertThat(retrievedBudget.get().getBudgetTotal()).isEqualTo(1000.0);
    }

    @Test
    void testDeleteById() {
        // Save a budget to the database
        Budget savedBudget = budgetRepository.save(new Budget(null, 1000.0, 500.0, 200.0, "Monthly", null,null));

        // Delete the budget by ID
        budgetRepository.deleteById(savedBudget.getId());

        // Attempt to retrieve the budget by ID
        Optional<Budget> retrievedBudget = budgetRepository.findById(savedBudget.getId());

        // Assertions
        assertThat(retrievedBudget).isNotPresent();
    }
}
