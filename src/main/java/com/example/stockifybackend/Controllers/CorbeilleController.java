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
    @PutMapping("/supprimerdefPermanentdeletedproduct/stockId={stockId}/supprimerPrmProductId={supprimerPrmProductId}")
    public ResponseEntity<?> updatePermanentProductDeletedInStcok(@PathVariable Long stockId,@PathVariable Long supprimerPrmProductId){
        Map<String, Object> response = new HashMap<>();
        corbeilleService.recupererPermanentProductInStcok(stockId,supprimerPrmProductId);
        response.put("message", "Product is deleted permanently");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/recupererdeletedrepas/stockId={stockId}/recupererRepasId={recupererRepasId}")
    public ResponseEntity<?> updateRepasIsDeletedInStcok(@PathVariable Long stockId, @PathVariable Long recupererRepasId) {
        Map<String, Object> response = new HashMap<>();
        corbeilleService.recupererRepasInStcok(stockId, recupererRepasId);
        response.put("message", "Repas:colonne isDeletd is updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/supprimerdefPermanentdeletedrepas/stockId={stockId}/supprimerPrmRecipetId={supprimerPrmRecipetId}")
    public ResponseEntity<?> updatePermanentRepasIsDeletedInStcok(@PathVariable Long stockId, @PathVariable Long supprimerPrmRecipetId) {
        Map<String, Object> response = new HashMap<>();
        corbeilleService.supprimerPermanentRepasInStcok(stockId, supprimerPrmRecipetId);
        response.put("message", "Repas is deleted permanently");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /*@PutMapping("/recupererdeletedrepas/stockId={stockId}/recupererRepasId={recupererRepasId}/quantity={quantity}")
    public ResponseEntity<?> updateRepasIsDeletedInStcok(@PathVariable Long stockId, @PathVariable Long recupererRepasId, @PathVariable int quantity) {
        Map<String, Object> response = new HashMap<>();
        corbeilleService.recupererRepasInStcok(stockId, recupererRepasId, quantity);
        response.put("message", "Repas is updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }*/

    @DeleteMapping("/supprmerdefdeletedproduct/stockId={stockId}/supprimerProductId={supprimerProductId}")
    public ResponseEntity<?> deleteDefProductIsDeletedInStcok(@PathVariable Long stockId,@PathVariable Long supprimerProductId){
        Map<String, Object> response = new HashMap<>();
        corbeilleService.supprimerDefProductFromStcok(stockId,supprimerProductId);
        response.put("message", "Product is deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/supprmerdefdeletedrecipe/stockId={stockId}/supprimerRepasId={supprimerRepasId}")
    public ResponseEntity<?> deleteDefRepasIsDeletedInStcok(@PathVariable Long stockId,@PathVariable Long supprimerRepasId){
        Map<String, Object> response = new HashMap<>();
        corbeilleService.supprimerDefRepasFromStcok(stockId,supprimerRepasId);
        response.put("message", "Repas is deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/viderproduits/{stockId}")
    public ResponseEntity<String> deleteAllDeletedProductsInStock(@PathVariable Long stockId) {
        String responseMessage = corbeilleService.deleteAllDeletedProductsInStock(stockId);
        return ResponseEntity.ok(responseMessage);
    }
    @DeleteMapping("/viderrepas/{stockId}")
    public ResponseEntity<String> deleteAllDeletedRecipesInStock(@PathVariable Long stockId) {
        String responseMessage = corbeilleService.deleteAllDeletedRecipesInStock(stockId);
        return ResponseEntity.ok(responseMessage);
    }
    @DeleteMapping("/vidercorbeille/{stockId}")
    public ResponseEntity<String> deleteAllDeletedProductsAndRecipesInStock(@PathVariable Long stockId) {
        String responseMessage = corbeilleService.deleteAllDeletedProductsAndRecipesInStock(stockId);
        return ResponseEntity.ok(responseMessage);
    }

}
