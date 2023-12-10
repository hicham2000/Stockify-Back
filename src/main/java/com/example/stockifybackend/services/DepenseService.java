package com.example.stockifybackend.services;

import com.example.stockifybackend.Entities.Depense;
import com.example.stockifybackend.Repositories.DepenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepenseService {

    private final DepenseRepository depenseRepository;

    @Autowired
    public DepenseService(DepenseRepository depenseRepository) {
        this.depenseRepository = depenseRepository;
    }

    public List<Depense> getAllDepenses() {
        return depenseRepository.findAll();
    }

    public Optional<Depense> getDepenseById(Long id) {
        return depenseRepository.findById(id);
    }

    public Depense saveDepense(Depense depense) {
        return depenseRepository.save(depense);
    }

    public void deleteDepense(Long id) {
        depenseRepository.deleteById(id);
    }
}
