package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.Entities.Depense;
import com.example.stockifybackend.services.DepenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/depenses")
public class DepenseController {

    private final DepenseService depenseService;

    @Autowired
    public DepenseController(DepenseService depenseService) {
        this.depenseService = depenseService;
    }

    @GetMapping
    public ResponseEntity<List<Depense>> getAllDepenses() {
        List<Depense> depenses = depenseService.getAllDepenses();
        return new ResponseEntity<>(depenses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Depense> getDepenseById(@PathVariable Long id) {
        Optional<Depense> depense = depenseService.getDepenseById(id);
        return depense.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Depense> addDepense(@RequestBody Depense depense) {
        Depense savedDepense = depenseService.saveDepense(depense);
        return new ResponseEntity<>(savedDepense, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Depense> updateDepense(@PathVariable Long id, @RequestBody Depense depense) {
        if (depenseService.getDepenseById(id).isPresent()) {
            depense.setId(id);
            Depense updatedDepense = depenseService.saveDepense(depense);
            return new ResponseEntity<>(updatedDepense, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepense(@PathVariable Long id) {
        if (depenseService.getDepenseById(id).isPresent()) {
            depenseService.deleteDepense(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
