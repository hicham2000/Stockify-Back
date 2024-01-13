package com.example.stockifybackend.services;

import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.Repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitServices {
    @Autowired
    private ProduitRepository produitRepository;
    public List<Produit> produitsList(Long stockId){
        return produitRepository.produits(stockId);
    }
}
