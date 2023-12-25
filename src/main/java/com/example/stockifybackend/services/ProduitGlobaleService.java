package com.example.stockifybackend.services;

import com.example.stockifybackend.Entities.ProduitGlobale;
import com.example.stockifybackend.Repositories.ProduitGlobaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitGlobaleService {

    private final ProduitGlobaleRepository produitGlobaleRepository;

    @Autowired
    public ProduitGlobaleService(ProduitGlobaleRepository produitGlobaleRepository) {
        this.produitGlobaleRepository = produitGlobaleRepository;
    }

    public List<ProduitGlobale> getAllProduits() {
        return produitGlobaleRepository.findAll();
    }

    public Optional<ProduitGlobale> getProduitById(Long id) {
        return produitGlobaleRepository.findById(id);
    }

    public ProduitGlobale saveProduit(ProduitGlobale produitGlobale) {
        return produitGlobaleRepository.save(produitGlobale);
    }

    public void deleteProduit(Long id) {
        produitGlobaleRepository.deleteById(id);
    }
}
