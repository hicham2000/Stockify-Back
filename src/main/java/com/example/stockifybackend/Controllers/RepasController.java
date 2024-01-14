package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.Entities.Repas;
import com.example.stockifybackend.Repositories.RepasRepository;
import com.example.stockifybackend.services.RepasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/listRepas")
public class RepasController {
    @Autowired
    private RepasService repasService;
    @GetMapping("/repas/{stockId}")
    public List<Repas> repasList(@PathVariable Long stockId){
        return repasService.listsDeRepas(stockId);
    }

}
