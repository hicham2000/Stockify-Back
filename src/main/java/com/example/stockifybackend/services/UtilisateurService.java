package com.example.stockifybackend.services;

import com.example.stockifybackend.Entities.Utilisateur;
import com.example.stockifybackend.Repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // add an utilisateur
    public Utilisateur addUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }
    // delete an utilisateur
    public void deleteUtilisateur(Long id) {
        Optional<Utilisateur> tempUtilisateur = utilisateurRepository.findById(id);
        if (tempUtilisateur.isEmpty()) {
            throw new RuntimeException("Utilisateur with id {\"+ id +\"} not found");
        }
        utilisateurRepository.delete(tempUtilisateur.get());
    }
    // get Utilisateur by id
    public Utilisateur getUtilisateur(Long id) {
        Optional<Utilisateur> tempUtilisateur = utilisateurRepository.findById(id);
        if (tempUtilisateur.isEmpty()) {
            throw new RuntimeException("Utilisateur with id {\"+ id +\"} not found");
        }
        return tempUtilisateur.get();
    }

}
