package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.Repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository ;


    @RequestMapping(value = "/Produits",method = RequestMethod.GET)
    public List<Produit> getReservations() {
        return produitRepository.findAll();
    }




}
