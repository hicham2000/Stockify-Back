package com.example.stockifybackend.services;

import com.example.stockifybackend.Entities.*;
import com.example.stockifybackend.Repositories.ProduitRepository;
import com.example.stockifybackend.Repositories.RecetteRepository;
import com.example.stockifybackend.Repositories.RepasRepository;
import com.example.stockifybackend.Repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CorbeilleService {
   @Autowired
   private StockService stockService;

   @Autowired
   private ProduitRepository produitRepository;

   @Autowired
   private RepasRepository repasRepository;



    public  List<Produit> getAllDeletedProducts(Long id){
        List<Produit> deletedProducts = new ArrayList<>();
        List<Produit> produit = stockService.getAllDeletedProductsInStock(id);
        for(Produit p : produit){
            if(p.getIs_deleted() == 1){
                deletedProducts.add(p);
            }
        }
        return deletedProducts;

    }
        public  List<Repas> getAllDeletedrecettes(Long id){
        List<Repas> deletedR = new ArrayList<>();
        List<Repas> deletedRecettes = stockService.getAllRecettesInStock(id);
        for(Repas r : deletedRecettes){
            if(r.getIs_deleted() == 1){
                deletedR.add(r);
            }
        }
        return deletedR;

    }

    public void recupererProductInStcok(Long stockId, Long productId){
        Optional<Produit> produitOptional = produitRepository.findById(productId);
        if (produitOptional.isPresent()) {
            Produit productUpdate = produitOptional.get();
            productUpdate.setIs_deleted(0);
            stockService.updateProduct(stockId, productId, productUpdate);
        }

    }
    public void recupererPermanentProductInStcok(Long stockId, Long productId){
        Optional<Produit> produitOptional = produitRepository.findById(productId);
        if (produitOptional.isPresent()) {
            Produit productUpdate = produitOptional.get();
            productUpdate.setPermanent(1);
            stockService.updateProduct(stockId, productId, productUpdate);
        }

    }
    
    public void supprimerDefRepasFromStcok(Long stockId, Long repasId){
        stockService.deleteRepasFromStock(stockId, repasId);

    }
    public void supprimerDefProductFromStcok(Long stockId, Long productId){

            stockService.deleteProductFromStock(stockId, productId);
    }

    public String  deleteAllDeletedProductsInStock(Long stockId) {
        List<Produit> deletedProducts = produitRepository.findAllDeletedProductsInStock(stockId);

        if (!deletedProducts.isEmpty()) {
            produitRepository.deleteAllDeletedProductsInStock(stockId);
            return "Deleted " + deletedProducts.size() + " products in stock with id " + stockId;
        } else {
            return "No products with is_deleted=1 found in stock with id " + stockId;
        }
    }
    public String  deletePermAllDeletedProductsInStock(Long stockId) {
        List<Produit> deletedProducts = produitRepository.findAllDeletedProductsInStock(stockId);

        if (!deletedProducts.isEmpty()) {
            produitRepository.updatePermanentFlagForDeletedProductsInStock(stockId);
            return "Deleted " + deletedProducts.size() + " products in stock with id " + stockId;
        } else {
            return "No products with is_deleted=1 found in stock with id " + stockId;
        }
    }
    public String  deleteAllDeletedRecipesInStock(Long stockId) {
        List<Repas> deletedRecipes = repasRepository.findAllDeletedRecipesInStock(stockId);

        if (!deletedRecipes.isEmpty()) {
            repasRepository.deleteAllDeletedRecipesInStock(stockId);
            return "Deleted " + deletedRecipes.size() + " recipes in stock with id " + stockId;
        } else {
            return "No recipes with is_deleted=1 found in stock with id " + stockId;
        }
    }
    public String  deletePermAllDeletedRecipesInStock(Long stockId) {
        List<Repas> deletedRecipes = repasRepository.findAllDeletedRecipesInStock(stockId);

        if (!deletedRecipes.isEmpty()) {
            repasRepository.updatePermanentFlagForDeletedRecipesInStock(stockId);
            return "Deleted permanently " + deletedRecipes.size() + " recipes in stock with id " + stockId;
        } else {
            return "No recipes with is_deleted=1 found in stock with id " + stockId;
        }
    }

    public String deleteAllDeletedProductsAndRecipesInStock(Long stockId) {
        List<Produit> deletedProducts = produitRepository.findAllDeletedProductsInStock(stockId);
        List<Repas> deletedRecipes = repasRepository.findAllDeletedRecipesInStock(stockId);

        if (!deletedProducts.isEmpty() || !deletedRecipes.isEmpty()) {
            produitRepository.deleteAllDeletedProductsInStock(stockId);
            repasRepository.deleteAllDeletedRecipesInStock(stockId);

            return "Deleted " + deletedProducts.size() + " products and " +
                    deletedRecipes.size() + " recipes in stock with id " + stockId;
        } else {
            return "No products or recipes with is_deleted=1 found in stock with id " + stockId;
        }
    }
    public void recupererRepasInStcok(Long stockId, Long recupererRepasId) {
        Optional<Repas> repasOptional = repasRepository.findById(recupererRepasId);
        if (repasOptional.isPresent()) {
            Repas repasUpdate = repasOptional.get();
            repasUpdate.setIs_deleted(0);
            stockService.updateRepas(stockId, recupererRepasId, repasUpdate);
        }
    }
    public void supprimerPermanentRepasInStcok(Long stockId, Long supprimerPrmRepasId) {
        Optional<Repas> repasOptional = repasRepository.findById(supprimerPrmRepasId);
        if (repasOptional.isPresent()) {
            Repas repasUpdate = repasOptional.get();
            repasUpdate.setPermanent(1);
            stockService.updateRepas(stockId, supprimerPrmRepasId, repasUpdate);
        }
    }
   /* public void recupererRepasInStcok(Long stockId, Long recupererRepasId, int quantity) {
        Optional<Repas> repasOptional = repasRepository.findById(recupererRepasId);
        if (repasOptional.isPresent()) {
            Repas repasUpdate = repasOptional.get();
            repasUpdate.setIs_deleted(0);

            // Update both the quantity and is_deleted
            stockService.updateRepas(stockId, recupererRepasId, repasUpdate, quantity);
        }
    }*/

}
