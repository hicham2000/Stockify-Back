package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.Entities.ProduitGlobale;
import com.example.stockifybackend.services.ProduitGlobaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/globals")
public class ProduitGlobaleController {

    private final ProduitGlobaleService produitGlobaleService;

    @Autowired
    public ProduitGlobaleController(ProduitGlobaleService produitGlobaleService) {
        this.produitGlobaleService = produitGlobaleService;
    }

    @GetMapping
    public ResponseEntity<List<ProduitGlobale>> getAllGlobals() {
        List<ProduitGlobale> globals = produitGlobaleService.getAllGlobals();
        return new ResponseEntity<>(globals, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduitGlobale> getGlobalById(@PathVariable Long id) {
        Optional<ProduitGlobale> global = produitGlobaleService.getGlobalById(id);
        return global.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ProduitGlobale> addGlobal(@RequestBody ProduitGlobale global) {
        ProduitGlobale savedGlobal = produitGlobaleService.saveGlobal(global);
        return new ResponseEntity<>(savedGlobal, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProduitGlobale> updateGlobal(@PathVariable Long id, @RequestBody ProduitGlobale global) {
        if (produitGlobaleService.getGlobalById(id).isPresent()) {
            global.setId(id);
            ProduitGlobale updatedGlobal = produitGlobaleService.saveGlobal(global);
            return new ResponseEntity<>(updatedGlobal, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGlobal(@PathVariable Long id) {
        if (produitGlobaleService.getGlobalById(id).isPresent()) {
            produitGlobaleService.deleteGlobal(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
