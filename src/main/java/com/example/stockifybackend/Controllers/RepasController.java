package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.Entities.Repas;
import com.example.stockifybackend.Repositories.RepasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/repas")
public class RepasController {
    @Autowired
    private RepasRepository reasRepository;
    @GetMapping("/{stockId}")
    public List<Repas> repasList(@PathVariable Long stockId){
        return reasRepository.repas(stockId);
    }

}
