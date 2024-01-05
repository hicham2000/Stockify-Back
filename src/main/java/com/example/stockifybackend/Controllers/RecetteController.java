package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.Entities.Recette;
import com.example.stockifybackend.Repositories.RecetteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/recettes")
public class RecetteController {
    @Autowired
    private RecetteRepository recetteRepository;

    @GetMapping("/{recetteId}")
    public ResponseEntity<?> getRecetteByID(@PathVariable Long recetteId) {
        Map<String, Object> response = new HashMap<>();
        Optional<Recette> OptionalRecette = recetteRepository.findById(recetteId);

        if(OptionalRecette.isPresent()){
            Recette recette = OptionalRecette.get();
            response.put("message", "Recette récupérer par succès");
            response.put("recette", recette);
        }

        response.put("message", "Aucun Recette avec id=" + recetteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
