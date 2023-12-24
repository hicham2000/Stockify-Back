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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UtilisateurApiController {
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostMapping("/Login")
    public ResponseEntity<?> authenticateUser(
            @RequestBody LogingUtilisateur logingUtilisateur
    ) {
        Optional<Utilisateur> utilisateurOptional = Optional.ofNullable(utilisateurService.getUtilisateurByEmail(logingUtilisateur.getEmail()));
        Map<String, Object> response = new HashMap<>();

        if (utilisateurOptional.isPresent()) {
            Utilisateur utilisateur = utilisateurOptional.get();
            if (utilisateur.getPassword().equals(logingUtilisateur.getPassword())) {
                response.put("message", "Login successfully :)");
                response.put("user_id", utilisateur.getId());

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
