package com.example.stockifybackend;

import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.Repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockifyBackendApplication implements CommandLineRunner {

    @Autowired
    private ProduitRepository produitRepository ;

    public static void main(String[] args) {

        SpringApplication.run(StockifyBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        this.produitRepository.save(new Produit("testproduct1"));
        this.produitRepository.save(new Produit("testproduct2"));
        this.produitRepository.save(new Produit("testproduct3"));
        this.produitRepository.save(new Produit("testproduct4"));
        this.produitRepository.save(new Produit("testproduct5"));
        this.produitRepository.save(new Produit("testproduct6"));
        this.produitRepository.save(new Produit("testproduct7"));
        this.produitRepository.save(new Produit("testproduct8"));
        this.produitRepository.save(new Produit("testproduct9"));
        this.produitRepository.save(new Produit("testproduct0"));
        this.produitRepository.save(new Produit("testproduct11"));
        this.produitRepository.save(new Produit("testproduct12"));
        this.produitRepository.save(new Produit("testproduct43"));
        this.produitRepository.save(new Produit("testproduct4345"));
        this.produitRepository.save(new Produit("testproduct345"));
        this.produitRepository.save(new Produit("testproduct4345"));
        this.produitRepository.save(new Produit("testproduct45345"));
        this.produitRepository.save(new Produit("testproduct435"));
        this.produitRepository.save(new Produit("testproduct3454"));
        this.produitRepository.save(new Produit("testproduc3545t4"));
        this.produitRepository.save(new Produit("testproduct3454"));
        this.produitRepository.save(new Produit("testproduct3454"));
        this.produitRepository.save(new Produit("testproductertre4"));
        this.produitRepository.save(new Produit("testproductert4"));
        this.produitRepository.save(new Produit("testproductert4"));

    }
}
