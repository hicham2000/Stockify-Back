package com.example.stockifybackend.services;

import com.example.stockifybackend.Entities.CategorieDeProduits;
import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.Repositories.CategorieDeProduitsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieDeProduitsService {
    @Autowired
    private CategorieDeProduitsRepository categorieDeProduitsRepository;

    public CategorieDeProduits addCategorie(CategorieDeProduits categorieDeProduits){
        return categorieDeProduitsRepository.save(categorieDeProduits);
    }
    public List<CategorieDeProduits> getAllCategories() {
        return categorieDeProduitsRepository.findAll();
    }
    public CategorieDeProduits getCategorie(Long id){
        Optional<CategorieDeProduits> tempCategorieDeProduits = categorieDeProduitsRepository.findById(id);
        if (tempCategorieDeProduits.isEmpty()){
            throw new RuntimeException("Categorie De Produits with id {\"+ id +\"} not found");
        }
        return tempCategorieDeProduits.get();
    }
    public List<Produit> getProduitsByCategorie(Long id) {
        Optional<CategorieDeProduits> categorieOptional = categorieDeProduitsRepository.findById(id);

        if (categorieOptional.isPresent()) {
            CategorieDeProduits categorie = categorieOptional.get();
            return categorie.getProduits();
        } else {
            throw new EntityNotFoundException("Catégorie non trouvée avec l'ID : " + id);
        }
    }

}
