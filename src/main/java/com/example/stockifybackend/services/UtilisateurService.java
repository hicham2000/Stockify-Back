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

    // update an utilisateur
        public void updateUtilisateur(Long id, Utilisateur updatedUtilisateur) {
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(id);

        if (optionalUtilisateur.isEmpty()) {
            throw new RuntimeException("Utilisateur with id " + id + " not found");
        }

        Utilisateur existingUtilisateur = optionalUtilisateur.get();

        // Update only the fields you want to allow updating
        existingUtilisateur.setPrénom(updatedUtilisateur.getPrénom());
        existingUtilisateur.setNom(updatedUtilisateur.getNom());
        existingUtilisateur.setEmail(updatedUtilisateur.getEmail());
        existingUtilisateur.setPassword(updatedUtilisateur.getPassword());
        existingUtilisateur.setRégimeSpécieux(updatedUtilisateur.getRégimeSpécieux());
        existingUtilisateur.setModeSportif(updatedUtilisateur.isModeSportif());
        existingUtilisateur.setSexe(updatedUtilisateur.getSexe());
        existingUtilisateur.setTaille(updatedUtilisateur.getTaille());
        existingUtilisateur.setPoids(updatedUtilisateur.getPoids());
        existingUtilisateur.setDateDeNaissance(updatedUtilisateur.getDateDeNaissance());

        // Save the updated utilisateur
        utilisateurRepository.save(existingUtilisateur);
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
    // get Utilisateur by email
    public Utilisateur getUtilisateurByEmail(String email) {
        Optional<Utilisateur> tempUtilisateur = utilisateurRepository.findByEmail(email);
        if (tempUtilisateur.isEmpty()) {
            throw new RuntimeException("Utilisateur with email {\"+ email +\"} not found");
        }
        return tempUtilisateur.get();
    }
    public boolean isUserExists(String email){
        return utilisateurRepository.existsByEmail(email);
    }
}
