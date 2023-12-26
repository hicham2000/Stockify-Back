package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.Entities.*;
import com.example.stockifybackend.services.CorbeilleService;
import com.example.stockifybackend.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/corbeille")

public class CorbeilleController {
    @Autowired
    private CorbeilleService corbeilleService;
    @Autowired
    private StockService stockService;
    @GetMapping("/deletedProduct/{Id}")
    public List<Produit> getDeletedProducts(@PathVariable Long Id) {
        List<Produit> DeletedProduits= corbeilleService.getAllDeletedProducts(Id);
        return DeletedProduits;
    }
    @GetMapping("/deletedRecipe/{Id}")
    public List<Repas> getDeletedRecipes(@PathVariable Long Id) {
        List<Repas> DeletedRecettes= corbeilleService.getAllDeletedrecettes(Id);
        return DeletedRecettes;
    }


    @PutMapping("/recupererdeletedproduct/stockId={stockId}/recupererProductId={recupererProductId}")
    public ResponseEntity<?> updateProductIsDeletedInStcok(@PathVariable Long stockId,@PathVariable Long recupererProductId){
        Map<String, Object> response = new HashMap<>();
        corbeilleService.recupererProductInStcok(stockId,recupererProductId);
        response.put("message", "Product is updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/supprmerdefdeletedproduct/stockId={stockId}/supprimerProductId={supprimerProductId}")
    public ResponseEntity<?> deleteDefProductIsDeletedInStcok(@PathVariable Long stockId,@PathVariable Long supprimerProductId){
        Map<String, Object> response = new HashMap<>();
        corbeilleService.supprimerDefProductFromStcok(stockId,supprimerProductId);
        response.put("message", "Product is deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/viderproduits/{stockId}")
    public ResponseEntity<String> deleteAllDeletedProductsInStock(@PathVariable Long stockId) {
        String responseMessage = corbeilleService.deleteAllDeletedProductsInStock(stockId);
        return ResponseEntity.ok(responseMessage);
    }
}
