package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.Repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository ;


    @RequestMapping(value = "/Produits",method = RequestMethod.GET)
    public List<Produit> getReservations() {

        List<Produit> a = new ArrayList();
        a= produitRepository.findAll();
        List<Produit> b = new ArrayList();

        for (int i = 0 ; i<a.size();i++){
            if(a.get(i).getPrix() == 0){
                b.add(a.get(i));
            }
        }

        return b;



    }




}
