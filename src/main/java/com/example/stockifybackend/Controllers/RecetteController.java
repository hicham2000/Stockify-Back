package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recettes")
public class RecetteController {
    @Autowired
    private RecetteService recetteService;
}
