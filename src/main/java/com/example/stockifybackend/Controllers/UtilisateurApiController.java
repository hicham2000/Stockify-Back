package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.Entities.Utilisateur;
import com.example.stockifybackend.services.AuthentificationService;
import com.example.stockifybackend.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UtilisateurApiController {
    @Autowired
    private UtilisateurService utilisateurService;
   //@Autowired
   //private AuthentificationService authService;

    @PostMapping("/Utilisateurs")
    public Utilisateur addUtilisateur(@RequestBody Utilisateur utilisateur){
        utilisateur.setId(0L);
        return utilisateurService.addUtilisateur(utilisateur);
    }
    @DeleteMapping("/Utilisateurs/{id}")
    public void deleteUtilisateur(@PathVariable Long id){
        utilisateurService.deleteUtilisateur(id);
    }
    @GetMapping("/Utilisateur/{id}")
    public Utilisateur getUtilisateur(@PathVariable Long id){
        return utilisateurService.getUtilisateur(id);
    }
    /*@PutMapping("/auth")
    public String AuthentificateUtilisateur(@RequestParam String email, @RequestParam String password){
        return authService.AuthentificateUtilisateur(email,password);
    }*/

}
