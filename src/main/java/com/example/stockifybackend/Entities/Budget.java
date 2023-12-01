package com.example.stockifybackend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Budget implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Number BudgetTotal;
    private Number BudgetDesProduitsConsommes;
    private Number BudgetDesProduitsGaspilles;
    private String PeriodiciteDeCalculDuBudget;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
    private List<Depense> depenses;
}
