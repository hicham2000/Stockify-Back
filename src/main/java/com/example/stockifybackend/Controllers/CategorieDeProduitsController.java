package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.Entities.CategorieDeProduits;
import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.services.CategorieDeProduitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorieDeProduits")
public class CategorieDeProduitsController {
    @Autowired
    private CategorieDeProduitsService categorieDeProduitsService;
    @PostMapping("/categorie")
    public ResponseEntity<String> addCategorie(@RequestBody CategorieDeProduits categorieDeProduits){
        categorieDeProduitsService.addCategorie(categorieDeProduits);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping
    public List<CategorieDeProduits> getAllCategories() {
        return categorieDeProduitsService.getAllCategories();
    }
    @GetMapping("/categorie{id}")
    public CategorieDeProduits getCategorie(@PathVariable Long id){
        return categorieDeProduitsService.getCategorie(id);
    }
    @GetMapping("/{id}/produits")
    public List<Produit> getCategorieDeProduits(@PathVariable Long id){
        return categorieDeProduitsService.getProduitsByCategorie(id);
    }
}
