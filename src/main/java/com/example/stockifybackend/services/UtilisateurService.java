package com.example.stockifybackend.services;

import com.example.stockifybackend.Entities.Utilisateur;
import com.example.stockifybackend.Repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
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

        /// Iterate through fields using reflection
        for (Field field : Utilisateur.class.getDeclaredFields()) {
            try {
                // Get the value of the field from updatedUtilisateur
                Object updatedValue = field.get(updatedUtilisateur);

                // Update only non-null values
                if (updatedValue != null) {
                    field.set(existingUtilisateur, updatedValue);
                }
            } catch (IllegalAccessException e) {
                // Handle exception if needed
                e.printStackTrace();
            }
        }

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
