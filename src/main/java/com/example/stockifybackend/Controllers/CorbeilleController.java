package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.Entities.Recette;
import com.example.stockifybackend.services.CorbeilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/corbeille")

public class CorbeilleController {
    @Autowired
    private CorbeilleService corbeilleService;
    @GetMapping("/deletedProduct/{Id}")
    public List<Produit> getDeletedProducts(@PathVariable Long Id) {
        List<Produit> DeletedProduits= corbeilleService.getAllDeletedProducts(Id);
        return DeletedProduits;
    }
    @GetMapping("/deletedRecette/{Id}")
    public List<Recette> getDeletedRecettes(@PathVariable Long Id) {
        List<Recette> DeletedRecettes= corbeilleService.getAllDeletedrecettes(Id);
        return DeletedRecettes;
    }



}
