package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.Entities.LogingUtilisateur;
import com.example.stockifybackend.Entities.Utilisateur;
import com.example.stockifybackend.Repositories.UtilisateurRepository;
import com.example.stockifybackend.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UtilisateurApiController {
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostMapping("/Login")
    public ResponseEntity<String> authenticateUser(@RequestBody LogingUtilisateur logingUtilisateur, HttpServletRequest request) {
        Optional<Utilisateur> utilisateurOptional = Optional.ofNullable(utilisateurService.getUtilisateurByEmail(logingUtilisateur.getEmail()));
        if(utilisateurOptional.isPresent()){
            Utilisateur utilisateur = utilisateurOptional.get();
            if(utilisateur.getPassword().equals(logingUtilisateur.getPassword())){
                HttpSession session = request.getSession(true);
                session.setAttribute("userId", utilisateur.getId());
                return new ResponseEntity<>("Login successfully :)", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Invalid credentials :(", HttpStatus.UNAUTHORIZED);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> addUtilisateur(@RequestBody Utilisateur utilisateur) {
        if (utilisateurService.isUserExists(utilisateur.getEmail())) {
            return new ResponseEntity<>("Utilisateur with this email already exists!",HttpStatus.BAD_REQUEST);
        }
        utilisateurService.addUtilisateur(utilisateur);
        return new ResponseEntity<>("You are registred Successfully!", HttpStatus.OK);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ResponseEntity<>("User logged out successfully!...", HttpStatus.OK);
    }

    @DeleteMapping("/Utilisateurs/{id}")
    public void deleteUtilisateur(@PathVariable Long id){
        utilisateurService.deleteUtilisateur(id);
    }
    @GetMapping("/Utilisateur/{id}")
    public Utilisateur getUtilisateur(@PathVariable Long id){
        return utilisateurService.getUtilisateur(id);
    }

}
