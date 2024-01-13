package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.Repositories.ProduitRepository;
import com.example.stockifybackend.services.ProduitServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@RestController("/Produits")
public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository ;
    @Autowired
    private ProduitServices produitServices;


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
    @GetMapping("/{StockId}")
    public List<Produit> produits(@PathVariable Long stockId) {
        return produitServices.produitsList(stockId);
    }




}
