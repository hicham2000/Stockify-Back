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

        this.produitRepository.save(new Produit(1L,"testproduct1"));
        this.produitRepository.save(new Produit(2L,"testproduct2"));
        this.produitRepository.save(new Produit(3L,"testproduct3"));
        this.produitRepository.save(new Produit(4L,"testproduct4"));

    }
}
