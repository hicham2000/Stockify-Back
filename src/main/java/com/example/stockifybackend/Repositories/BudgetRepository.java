package com.example.stockifybackend.Repositories;


import com.example.stockifybackend.Entities.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
}
