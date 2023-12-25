package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.Entities.ProduitGlobale;
import com.example.stockifybackend.services.ProduitGlobaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/global")
public class ProduitGlobaleController {

    private final ProduitGlobaleService produitGlobaleService;

    @Autowired
    public ProduitGlobaleController(ProduitGlobaleService produitGlobaleService) {
        this.produitGlobaleService = produitGlobaleService;
    }

    @GetMapping
    public List<ProduitGlobale> getAllGlobals() {
        return produitGlobaleService.getAllGlobals();
    }

    @GetMapping("/{id}")
    public Optional<ProduitGlobale> getGlobalById(@PathVariable Long id) {
        return produitGlobaleService.getGlobalById(id);
    }

    @PostMapping
    public ProduitGlobale updateGlobal(@RequestBody ProduitGlobale produitGlobale) {
        return produitGlobaleService.saveGlobal(produitGlobale);
    }

    @DeleteMapping("/{id}")
    public void deleteGlobal(@PathVariable Long id) {
        produitGlobaleService.deleteGlobal(id);
    }
}
