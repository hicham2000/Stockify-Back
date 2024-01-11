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
import java.util.HashMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @PostMapping("/{stockId}/recipes/{recetteId}")
    public ResponseEntity<?> addRecipeToStockByRecetteID(@PathVariable Long stockId, @PathVariable Long recetteId) {
        Map<String, Object> response = new HashMap<>();
        try {
            stockService.addRecipeToStockByRecetteId(stockId, recetteId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.put("message", "Recette ajouté au stock avec succès");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{stockId}/recipes/{recipeId}")
    public ResponseEntity<Void> deleteRecipeFromStock(@PathVariable Long stockId, @PathVariable Long recipeId) {
        stockService.deleteRecipeFromStock(stockId, recipeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{stockId}/recipes/{recipeId}")
    public ResponseEntity<String> updateRecipe(@PathVariable Long stockId, @PathVariable Long recipeId, @RequestBody Recette updatedRecette) {
        stockService.updateRecipe(stockId, recipeId, updatedRecette);
        return ResponseEntity.ok("Recipe updated successfully");
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

    @DeleteMapping("/{stockId}/products/{productId}/delete-course")
    public ResponseEntity<Void> deleteProductFromStockCourse(@PathVariable Long stockId, @PathVariable Long productId) {
        stockService.deleteProductFromStockCourse(stockId, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{stockId}/products/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Long stockId, @PathVariable Long productId, @RequestBody Produit updatedProduit) {
        stockService.updateProduct(stockId, productId, updatedProduit);
        return ResponseEntity.ok("Product updated successfully");
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
        return stockService.getStock(stockId);
    }

    @PutMapping("/{stockId}/{newQuantiteCritiqueParDefault}")
    public ResponseEntity<?> updateQuantiteCritiqueParDefaut(@PathVariable Long stockId, @PathVariable int newQuantiteCritiqueParDefault){
        Map<String, Object> response = new HashMap<>();
        try{
            stockService.updateQuantiteCritiqueParDefaut(stockId, newQuantiteCritiqueParDefault);
        }catch(Exception error){
            response.put("Error", error);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "QuantiteCritiqueParDefault du stock with stock_id="+stockId+" updated successfully!...");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}

class RequestBodyData {
    private String intitule;
    private String datePeremtion;
    private String dateAlert;
    private String stock;

    private String categorie;
    private List<Produit> arraylist_of_product;
    private String spinnerText;

    public RequestBodyData(String intitule, String datePeremtion, String dateAlert, String hello,String categorie, List<Produit> arraylist_of_product, String spinnerText) {
        this.intitule = intitule;
        this.datePeremtion = datePeremtion;
        this.dateAlert = dateAlert;
        this.stock = hello;
        this.categorie = categorie;
        this.arraylist_of_product = arraylist_of_product;
        this.spinnerText = spinnerText;
    }

    public String getIntitule() {
        return intitule;
    }

    public String getDatePeremtion() {
        return datePeremtion;
    }

    public String getDateAlert() {
        return dateAlert;
    }

    public String getStock() {
        return stock;
    }

    public String getCategorie() {
        return categorie;
    }

    public List<Produit> getArraylist_of_product() {
        return arraylist_of_product;
    }

    public String getSpinnerText() {
        return spinnerText;
    }
}


