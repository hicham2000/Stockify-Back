package com.example.stockifybackend.services;

import com.example.stockifybackend.Entities.CategorieDeProduits;
import com.example.stockifybackend.Repositories.CategorieDeProduitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategorieDeProduitsService {
    @Autowired
    private CategorieDeProduitsRepository categorieDeProduitsRepository;

    public CategorieDeProduits addCategorie(CategorieDeProduits categorieDeProduits){
        return categorieDeProduitsRepository.save(categorieDeProduits);
    }
    public CategorieDeProduits getCategorie(Long id){
        Optional<CategorieDeProduits> tempCategorieDeProduits = categorieDeProduitsRepository.findById(id);
        if (tempCategorieDeProduits.isEmpty()){
            throw new RuntimeException("Categorie De Produits with id {\"+ id +\"} not found");
        }
        return tempCategorieDeProduits.get();
    }

}
