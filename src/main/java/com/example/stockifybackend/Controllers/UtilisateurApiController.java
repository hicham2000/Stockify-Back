package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.Entities.LogingUtilisateur;
import com.example.stockifybackend.Entities.Utilisateur;
import com.example.stockifybackend.Repositories.UtilisateurRepository;
import com.example.stockifybackend.dto.UtilisateurUpdateRequest;
import com.example.stockifybackend.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
public class UtilisateurApiController {
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostMapping("/Login")
    public ResponseEntity<?> authenticateUser(@RequestBody LogingUtilisateur logingUtilisateur) {
        Optional<Utilisateur> utilisateurOptional = Optional.ofNullable(utilisateurService.getUtilisateurByEmail(logingUtilisateur.getEmail()));
        Map<String, Object> response = new HashMap<>();

        if (utilisateurOptional.isPresent()) {
            Utilisateur utilisateur = utilisateurOptional.get();
            if (utilisateur.getPassword().equals(logingUtilisateur.getPassword())) {
                response.put("message", "Login successfully :)");
                response.put("user_id", utilisateur.getId());
                response.put("stock_id", utilisateur.getStock().getId());
                response.put("listeDeCourse_id", utilisateur.getListeDeCourse_id());

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        response.put("message", "Invalid credentials :(");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> addUtilisateur(@RequestBody Utilisateur utilisateur) {
        Map<String, Object> response = new HashMap<>();

        if (utilisateurService.isUserExists(utilisateur.getEmail())) {
            response.put("message", "Utilisateur with this email already exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }


        utilisateurService.addUtilisateur(utilisateur);
        response.put("message", "You are registred Successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.put("message", "User logged out successfully!...");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/Utilisateurs/{id}")
    public ResponseEntity<?> deleteUtilisateur(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try {
            utilisateurService.deleteUtilisateur(id);
        }catch(Exception error){
            response.put("Error", error);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "User Deleted successfully!...");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/Utilisateur/{id}")
    public Utilisateur getUtilisateur(@PathVariable Long id){
        return utilisateurService.getUtilisateur(id);
    }

    @PutMapping("/Utilisateur/{id}")
    public ResponseEntity<?> updateUtilisateur(@RequestBody UtilisateurUpdateRequest updatedUtilisateur, @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();

        try {
            utilisateurService.updateUtilisateurFields(id, updatedUtilisateur);
        }catch(Exception error){
            response.put("Error", error);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "User updated successfully!...");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/Utilisateur/{id}/sexe/{sexe}")
    public ResponseEntity<String> updateUtilisateurSexe(@PathVariable Long id, @PathVariable String sexe){
        Map<String, Object> response = new HashMap<>();
        try {
            utilisateurService.updateUtilisateurFieldsSexe(id, sexe);
        }catch(Exception error){
            response.put("Error", error);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        response.put("message", "User updated successfully!...");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/Utilisateur/{id}/date/{date}")
    public ResponseEntity<String> updateUtilisateurDate(@PathVariable Long id, @PathVariable Date date){
        Map<String, Object> response = new HashMap<>();
        try {
            utilisateurService.updateUtilisateurFieldsDate(id, date);
        }catch(Exception error){
            response.put("Error", error);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        response.put("message", "User updated successfully!...");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/Utilisateur/{id}/taille/{nouvelletaille}")
    public ResponseEntity<String> updateUtilisateurTaille(@PathVariable Long id, @PathVariable String nouvelletaille){

            utilisateurService.updateUtilisateurFieldsTaille(id, nouvelletaille);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/Utilisateur/{id}/poids/{poids}")
    public ResponseEntity<String> updateUtilisateurPoids(@PathVariable Long id, @PathVariable String poids){

        utilisateurService.updateUtilisateurFieldsPoids(id, poids);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/Utilisateur/{id}/regime/{regime}")
    public ResponseEntity<String> updateUtilisateurRegime(@PathVariable Long id, @PathVariable String regime){

        utilisateurService.updateUtilisateurFieldsRegime(id, regime);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/Utilisateur/{id}/sportif/{regime}")
    public ResponseEntity<String> updateUtilisateurSportif(@PathVariable Long id, @PathVariable Boolean regime){

        utilisateurService.updateUtilisateurFieldsMode(id, regime);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/Utilisateur/{id}/alerte/{regime}")
    public ResponseEntity<String> updateUtilisateurAlerte(@PathVariable Long id, @PathVariable Boolean regime){

        utilisateurService.updateUtilisateurFieldsAlerteExp(id, regime);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/Utilisateur/{id}/alertePeremp/{regime}")
    public ResponseEntity<String> updateUtilisateurAlertePerem(@PathVariable Long id, @PathVariable Boolean regime){

        utilisateurService.updateUtilisateurFieldsAlerteQuantiteCr(id, regime);

        return new ResponseEntity<>(HttpStatus.OK);
    }

  //  @PutMapping("/Utilisateur/{id}/taille/{taille}")
 //   public ResponseEntity<String> updateUtilisateurUniteTaille(@PathVariable Long id, @PathVariable String taille){

   //     utilisateurService.updateUtilisateurFieldsUniteTaille(id, taille);

     //   return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   // }

  //  @PutMapping("/Utilisateur/{id}/poids/{poid}")
 //   public ResponseEntity<String> updateUtilisateurUnitePoids(@PathVariable Long id, @PathVariable String taille){

   //     utilisateurService.updateUtilisateurFieldsUnitePoids(id, taille);

     //   return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   // }



    @PostMapping("/Utilisateur/{userId}/recetteFavoris/{recette_id}")
    public ResponseEntity<?> ajouterRecetteFavorite(@PathVariable Long userId, @PathVariable Long recette_id) {
        Map<String, Object> response = new HashMap<>();
        try {
            utilisateurService.AddRecetteAuFavoris(userId, recette_id);
        }catch(Exception error){
            response.put("Error", error);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Recette ajoutée aux favoris avec succès");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/Utilisateurs/{userId}/recetteFavoris/{recette_id}")
    public ResponseEntity<?> supprimerRecetteFavorite(@PathVariable Long userId, @PathVariable Long recette_id) {
        Map<String, Object> response = new HashMap<>();
        try {
            utilisateurService.removeRecetteAuFavoris(userId, recette_id);
        }catch(Exception error){
            response.put("Error", error);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Recette supprimée de favoris avec succès");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

