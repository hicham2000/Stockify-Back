package com.example.stockifybackend.services;

import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.Entities.Recette;
import com.example.stockifybackend.Entities.Stock;
import com.example.stockifybackend.Repositories.ProduitRepository;
import com.example.stockifybackend.Repositories.RecetteRepository;
import com.example.stockifybackend.Repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CorbeilleService {
   @Autowired
   private StockService stockService;

    public  List<Produit> getAllDeletedProducts(Long id){
        List<Produit> deletedProducts = new ArrayList<>();
        List<Produit> produit = stockService.getAllProductsInStock(id);
        for(Produit p : produit){
            if(p.getIs_deleted() == 1){
                deletedProducts.add(p);
            }
        }
        return deletedProducts;

    }
    public  List<Recette> getAllDeletedrecettes(Long id){
        List<Recette> deletedR = new ArrayList<>();
        List<Recette> deletedRecettes = stockService.getAllRecipesInStock(id);
        for(Recette r : deletedRecettes){
            if(r.getIs_deleted() == 1){
                deletedR.add(r);
            }
        }
        return deletedR;

    }

}
