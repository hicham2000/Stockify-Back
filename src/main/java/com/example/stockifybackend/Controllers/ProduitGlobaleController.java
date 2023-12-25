package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.Entities.ProduitGlobale;
import com.example.stockifybackend.services.ProduitGlobaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produits")
public class ProduitGlobaleController {

    private final ProduitGlobaleService produitGlobaleService;

    @Autowired
    public ProduitGlobaleController(ProduitGlobaleService produitGlobaleService) {
        this.produitGlobaleService = produitGlobaleService;
    }

    @GetMapping
    public List<ProduitGlobale> getAllProduits() {
        return produitGlobaleService.getAllProduits();
    }

    @GetMapping("/{id}")
    public Optional<ProduitGlobale> getProduitById(@PathVariable Long id) {
        return produitGlobaleService.getProduitById(id);
    }

    @PostMapping
    public ProduitGlobale saveProduit(@RequestBody ProduitGlobale produitGlobale) {
        return produitGlobaleService.saveProduit(produitGlobale);
    }

    @DeleteMapping("/{id}")
    public void deleteProduit(@PathVariable Long id) {
        produitGlobaleService.deleteProduit(id);
    }
}
