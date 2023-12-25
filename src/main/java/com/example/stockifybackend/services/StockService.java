package com.example.stockifybackend.services;


import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.Entities.Recette;
import com.example.stockifybackend.Entities.Repas;
import com.example.stockifybackend.Entities.Stock;
import com.example.stockifybackend.Repositories.ProduitRepository;
import com.example.stockifybackend.Repositories.RecetteRepository;
import com.example.stockifybackend.Repositories.RepasRepository;
import com.example.stockifybackend.Repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private RecetteRepository recetteRepository;

    @Autowired
    private RepasRepository repasRepository;


    public void addRecipeToStock(Long stockId, Recette recette) {
        Optional<Stock> optionalStock = stockRepository.findById(stockId);

        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            recette.setStock(stock);
            recetteRepository.save(recette);
            stock.getRecette().add(recette);
            stockRepository.save(stock);
        } else {
            throw new RuntimeException("There is no stock with this id");
        }
    }

    public void deleteRecipeFromStock(Long stockId, Long recetteId) {
        Optional<Stock> optionalStock = stockRepository.findById(stockId);

        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            List<Recette> recettes = stock.getRecette();

            recettes.removeIf(recette -> recette.getId().equals(recetteId));
            stockRepository.deleteById(recetteId);

            stockRepository.save(stock);
        } else {
            throw new RuntimeException("There is no stock with this id");
        }
    }

    public void updateRecipe(Long stockId, Long recipeId, Recette updatedRecette) {
        Optional<Stock> optionalStock = stockRepository.findById(stockId);

        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            List<Recette> recettes = stock.getRecette();

            for (Recette recette : recettes) {
                if (recette.getId().equals(recipeId)) {

                    recette.setIntitule(updatedRecette.getIntitule());
                    recette.setDesctipion(updatedRecette.getDesctipion());
                    recette.setDureeTotal(updatedRecette.getDureeTotal());
                    recette.setRecommendation(updatedRecette.getRecommendation());

                    recetteRepository.save(recette);
                    return;
                }
            }

            throw new RuntimeException("Recipe with id " + recipeId + " does not exist in this stock");
        } else {
            throw new RuntimeException("There is no stock with this id");
        }
    }

    public void addProductToStock(Long stockId, Produit produit) {
        Optional<Stock> optionalStock = stockRepository.findById(stockId);

        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            produit.setStock(stock);
            produitRepository.save(produit);
            stock.getProduit().add(produit);
            stockRepository.save(stock);
        } else {
            throw new RuntimeException("There is no stock with this id");
        }
    }


    public void deleteProductFromStock(Long stockId, Long produitId) {
        Optional<Stock> optionalStock = stockRepository.findById(stockId);

        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            List<Produit> produits = stock.getProduit();

            produits.removeIf(produit -> produit.getId().equals(produitId));
            produitRepository.deleteById(produitId);
            stockRepository.save(stock);
        } else {
            throw new RuntimeException("There is no stock with this id");
        }
    }

    public void updateProduct(Long stockId, Long productId, Produit updatedProduit) {
        Optional<Stock> optionalStock = stockRepository.findById(stockId);

        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            List<Produit> produits = stock.getProduit();

            for (Produit produit : produits) {
                if (produit.getId().equals(productId)) {

                    produit.setIntitule(updatedProduit.getIntitule());
                    produit.setDescription(updatedProduit.getDescription());
                    produit.setBrande(updatedProduit.getBrande());
                    produit.setUniteDeMesure(updatedProduit.getUniteDeMesure());
                    produit.setDateExpiration(updatedProduit.getDateExpiration());
                    produit.setQuantite(updatedProduit.getQuantite());
                    produit.setPrix(updatedProduit.getPrix());
                    produit.setQuantiteCritique(updatedProduit.getQuantiteCritique());
                    produit.setValeurNutritionnel(updatedProduit.getValeurNutritionnel());
                    produit.setCategories(updatedProduit.getCategories());

                    produitRepository.save(produit);
                    stockRepository.save(stock);
                    return;
                }
            }

            throw new RuntimeException("Product with id " + productId + " does not exist in this stock");
        } else {
            throw new RuntimeException("There is no stock with this id");
        }
    }

    public List<Produit> getAllProductsInStock(Long stockId) {
        return produitRepository.findAllByStockIdCustomQuery(stockId);


    }

    public List<Recette> getAllRecipesInStock(Long stockId) {
        return recetteRepository.findAllByStockIdCustomQuery(stockId);
    }
    public List<Repas> getAllRecettesInStock(Long stockId) {
        return repasRepository.findAllRepasByStockIdCustomQuery(stockId);
    }



}
