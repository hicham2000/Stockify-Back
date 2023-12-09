package com.example.stockifybackend.Controllers;


import com.example.stockifybackend.Entities.Recette;
import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.Entities.Stock;
import com.example.stockifybackend.Repositories.StockRepository;
import com.example.stockifybackend.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @PostMapping("/{stockId}/recipes")
    public ResponseEntity<Void> addRecipeToStock(@PathVariable Long stockId, @RequestBody Recette recette) {
        stockService.addRecipeToStock(stockId, recette);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @DeleteMapping("/{stockId}/recipes/{recipeId}")
    public ResponseEntity<Void> deleteRecipeFromStock(@PathVariable Long stockId, @PathVariable Long recipeId) {
        stockService.deleteRecipeFromStock(stockId, recipeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/{stockId}/products")
    public ResponseEntity<Void> addProductToStock(@PathVariable Long stockId, @RequestBody Produit produit) {
        stockService.addProductToStock(stockId, produit);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @DeleteMapping("/{stockId}/products/{productId}")
    public ResponseEntity<Void> deleteProductFromStock(@PathVariable Long stockId, @PathVariable Long productId) {
        stockService.deleteProductFromStock(stockId, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/{stockId}/products")
    public List<Produit> getAllProductsInStock(@PathVariable Long stockId) {
        return stockService.getAllProductsInStock(stockId);
    }

    @GetMapping("/{stockId}/recipes")
    public ResponseEntity<List<Recette>> getAllRecipesInStock(@PathVariable Long stockId) {
        List<Recette> recipes = stockService.getAllRecipesInStock(stockId);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("/{stockId}")
    public Stock getStockById(@PathVariable Long stockId) {
        Optional<Stock> stockOptional = stockRepository.findById(stockId);


        if (stockOptional.isPresent()) {
            return stockOptional.get();
        } else {
            return null;
        }
    }
}
