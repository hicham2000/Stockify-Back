package com.example.stockifybackend.controllers;

import com.example.stockifybackend.Entities.Intolerance;
import com.example.stockifybackend.services.IntoleranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/intolerances")
public class IntoleranceController {

    private final IntoleranceService intoleranceService;

    @Autowired
    public IntoleranceController(IntoleranceService intoleranceService) {
        this.intoleranceService = intoleranceService;
    }

    @GetMapping
    public ResponseEntity<List<Intolerance>> getAllIntolerances() {
        List<Intolerance> intolerances = intoleranceService.getAllIntolerances();
        return new ResponseEntity<>(intolerances, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Intolerance> getIntoleranceById(@PathVariable Long id) {
        Optional<Intolerance> intolerance = intoleranceService.getIntoleranceById(id);
        return intolerance.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Intolerance> createIntolerance(@RequestBody Intolerance intolerance) {
        Intolerance savedIntolerance = intoleranceService.saveIntolerance(intolerance);
        return new ResponseEntity<>(savedIntolerance, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Intolerance> updateIntolerance(@PathVariable Long id, @RequestBody Intolerance intolerance) {
        if (intoleranceService.getIntoleranceById(id).isPresent()) {
            intolerance.setId(id);
            Intolerance updatedIntolerance = intoleranceService.saveIntolerance(intolerance);
            return new ResponseEntity<>(updatedIntolerance, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIntolerance(@PathVariable Long id) {
        if (intoleranceService.getIntoleranceById(id).isPresent()) {
            intoleranceService.deleteIntolerance(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
