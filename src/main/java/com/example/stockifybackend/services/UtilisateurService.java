package com.example.stockifybackend.services;

import com.example.stockifybackend.Entities.Utilisateur;
import com.example.stockifybackend.Repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
    public void updateUtilisateurFields(Long id, Utilisateur updatedUtilisateur) throws ParseException {
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(id);

        if (optionalUtilisateur.isEmpty()) {
            throw new RuntimeException("Utilisateur with id " + id + " not found");
        }

        Utilisateur existingUtilisateur = optionalUtilisateur.get();

        // Update only the non-null fields
        if (updatedUtilisateur.getPrénom() != null) {
            existingUtilisateur.setPrénom(updatedUtilisateur.getPrénom());
        }
        if (updatedUtilisateur.getNom() != null) {
            existingUtilisateur.setNom(updatedUtilisateur.getNom());
        }
        if (updatedUtilisateur.getEmail() != null) {
            existingUtilisateur.setEmail(updatedUtilisateur.getEmail());
        }
        if (updatedUtilisateur.getPassword() != null) {
            existingUtilisateur.setPassword(updatedUtilisateur.getPassword());
        }
        if (updatedUtilisateur.getRégimeSpécieux() != null) {
            existingUtilisateur.setRégimeSpécieux(updatedUtilisateur.getRégimeSpécieux());
        }
        if (updatedUtilisateur.isModeSportif() != existingUtilisateur.isModeSportif()) {
            existingUtilisateur.setModeSportif(updatedUtilisateur.isModeSportif());
        }
        if (updatedUtilisateur.getSexe() != null) {
            existingUtilisateur.setSexe(updatedUtilisateur.getSexe());
        }
        if (updatedUtilisateur.getTaille() != null) {
            existingUtilisateur.setTaille(updatedUtilisateur.getTaille());
        }
        if (updatedUtilisateur.getPoids() != null) {
            existingUtilisateur.setPoids(updatedUtilisateur.getPoids());
        }
        if (updatedUtilisateur.getDateDeNaissance() != null) {
            try {
                Date updatedDate = parseDate(updatedUtilisateur.getDateDeNaissance().toString());
                existingUtilisateur.setDateDeNaissance(updatedDate);
            } catch (ParseException e) {
                // Handle parsing exception (log or rethrow)
                e.printStackTrace();
                // You might want to log or rethrow the exception based on your error handling strategy
                // throw new RuntimeException("Failed to parse date", e);
            }
        }

        // Save the updated utilisateur
        utilisateurRepository.save(existingUtilisateur);
    }

    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        return dateFormat.parse(dateString);
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
