package com.example.stockifybackend;

import com.example.stockifybackend.Entities.CategorieDeProduits;
import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.Entities.ValeurNutritionnel;
import com.example.stockifybackend.Repositories.CategorieDeProduitsRepository;
import com.example.stockifybackend.Repositories.ProduitRepository;
import com.example.stockifybackend.Repositories.ValeurNutritionnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockifyBackendApplication implements CommandLineRunner {

    @Autowired
    private ProduitRepository produitRepository ;

    @Autowired
    private CategorieDeProduitsRepository categorieDeProduitsRepository;

    @Autowired
    private ValeurNutritionnelRepository valeurNutritionnelRepository;

    public static void main(String[] args) {

        SpringApplication.run(StockifyBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        CategorieDeProduits categorieDeProduits = new CategorieDeProduits();
        categorieDeProduits.setIntitule("hicham");

        ValeurNutritionnel valeur = new ValeurNutritionnel(1L,3,4,6,7,3,4);
        this.valeurNutritionnelRepository.save(valeur);



        Produit p = new Produit("testproduct1");
        p.setIntitule("ghj");
        p.setValeurNutritionnel(valeur);
        this.produitRepository.save(p);
//        categorieDeProduits.setProduit(p);
//        this.categorieDeProduitsRepository.save(categorieDeProduits);



    }
}
