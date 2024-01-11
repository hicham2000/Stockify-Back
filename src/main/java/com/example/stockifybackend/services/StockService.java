package com.example.stockifybackend.services;


import com.example.stockifybackend.Entities.*;
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
import java.util.stream.Collectors;

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

    public Stock getStock(long stockId){
        Optional<Stock> optionalStock = stockRepository.findById(stockId);
        if (optionalStock.isPresent()) {
            return optionalStock.get();
        } else {
            throw new RuntimeException("Stock with id " + stockId + " does not exist in this stock");
        }
    }

    public void updateQuantiteCritiqueParDefaut(Long stockId, int nouveauQuantiteCritiqueParDefaut) {
        Optional<Stock> optionalStock = stockRepository.findById(stockId);
        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            stock.setQuantiteCritiqueParDefaut(nouveauQuantiteCritiqueParDefaut);
            stockRepository.save(stock);
        } else {
            throw new RuntimeException("Stock with id " + stockId + " does not exist in this stock");
        }
    }


    public void addRecipeToStock(Long stockId, Recette recette) {
        Optional<Stock> optionalStock = stockRepository.findById(stockId);

        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            recette.setStock(stock);
            recetteRepository.saveAndFlush(recette);
            stock.getRecette().add(recette);
            stockRepository.saveAndFlush(stock);
        } else {
            throw new RuntimeException("There is no stock with this id");
        }
    }

    public void addRecipeToStockByRecetteId(Long stockId, Long recetteId){
        Optional<Stock> optionalStock = stockRepository.findById(stockId);

        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            Optional<Recette> optionalRecette = recetteRepository.findById(recetteId);
            if(optionalRecette.isPresent()){
                Recette recette = optionalRecette.get();
                recette.setStock(stock);
                recetteRepository.saveAndFlush(recette);
                stock.getRecette().add(recette);
                stockRepository.saveAndFlush(stock);
            } else {
                throw new RuntimeException("There is no recette with this id=" + stockId);
            }
        } else {
            throw new RuntimeException("There is no stock with this id=" + recetteId);
        }
    }

    public void deleteRecipeFromStock(Long stockId, Long recetteId) {
        Optional<Stock> optionalStock = stockRepository.findById(stockId);

        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            List<Recette> recettes = stock.getRecette();

            recettes.removeIf(recette -> recette.getId().equals(recetteId));

            Optional<Recette> optionalRecette = recetteRepository.findById(recetteId);
            if (optionalRecette.isPresent()) {
                Recette recette = optionalRecette.get();
                recette.setStock(null);
                recetteRepository.saveAndFlush(recette);
            } else {
                throw new RuntimeException("There is no recipe with this id");
            }

            stock = stockRepository.saveAndFlush(stock);
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
                    recette.setDescription(updatedRecette.getDescription());
                    recette.setDureeTotal(updatedRecette.getDureeTotal());

                    recetteRepository.save(recette);
                    return;
                }
            }

            throw new RuntimeException("Recipe with id " + recipeId + " does not exist in this stock");
        } else {
            throw new RuntimeException("There is no stock with this id");
        }
    }
    /*public void updateRepas(Long stockId, Long repasId, Repas repasUpdate, int quantity) {
        Optional<Stock> optionalStock = stockRepository.findById(stockId);

        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            List<Repas> repasList = stock.getRepas();
            for (Repas repas : repasList) {
                if (repas.getId().equals(repasId)) {

                    //repas.setQuantity(quantity);


                    repasRepository.save(repas);
                    return;
                }
            }
            // Update the quantity of the repas


            // Your other update logic goes here

            stockRepository.save(stock);
        } else {
            throw new RuntimeException("There is no stock with this id");
        }
    }*/

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

    public void deleteProductFromStockCourse(Long stockId, Long produitId) {
        Optional<Stock> optionalStock = stockRepository.findById(stockId);

        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            List<Produit> produits = stock.getProduit();


            Optional<Produit> produitToDelete = produitRepository.findById_produitCourse(produitId);

            // Si le Produit est trouvé, supprimez-le de la liste
            produitToDelete.ifPresent(produit -> produits.remove(produit));

            // Ensuite, supprimez le Produit de la base de données
            produitToDelete.ifPresent(produit -> produitRepository.delete(produit));


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
                    //produit.setDescription(updatedProduit.getDescription());
                    produit.setBrande(updatedProduit.getBrande());
                    produit.setUniteDeMesure(updatedProduit.getUniteDeMesure());
                    produit.setDateExpiration(updatedProduit.getDateExpiration());
                    produit.setDateAlerte(updatedProduit.getDateAlerte());
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
    public List<Produit> getAllDeletedProductsInStock(Long stockId) {
        return produitRepository.findAllDeletedProductsInStock(stockId);
    }

    public List<Recette> getAllRecipesInStock(Long stockId) {
        return recetteRepository.findAllByStockIdCustomQuery(stockId);
    }
    public List<Repas> getAllRecettesInStock(Long stockId) {
        return repasRepository.findAllRepasByStockIdCustomQuery(stockId);
    }

    public void deleteRepasFromStock(Long stockId, Long repasId) {
        Optional<Stock> optionalStock = stockRepository.findById(stockId);

        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            List<Repas> recipes = stock.getRepas();

            recipes.removeIf(recipe -> recipe.getId().equals(repasId));
            repasRepository.deleteById(repasId);

            stockRepository.save(stock);
        } else {
            throw new RuntimeException("There is no stock with this id");
        }
    }


}
