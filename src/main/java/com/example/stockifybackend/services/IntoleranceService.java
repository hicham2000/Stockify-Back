package com.example.stockifybackend.services;

import com.example.stockifybackend.Entities.Intolerance;
import com.example.stockifybackend.Repositories.IntoleranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IntoleranceService {

    private final IntoleranceRepository intoleranceRepository;

    @Autowired
    public IntoleranceService(IntoleranceRepository intoleranceRepository) {
        this.intoleranceRepository = intoleranceRepository;
    }

    public List<Intolerance> getAllIntolerances() {
        return intoleranceRepository.findAll();
    }

    public Optional<Intolerance> getIntoleranceById(Long id) {
        return intoleranceRepository.findById(id);
    }

    public Intolerance saveIntolerance(Intolerance intolerance) {
        return intoleranceRepository.save(intolerance);
    }

    public void deleteIntolerance(Long id) {
        intoleranceRepository.deleteById(id);
    }
}
