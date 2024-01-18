package com.example.stockifybackend.services;

import aj.org.objectweb.asm.Handle;
import com.example.stockifybackend.Entities.ListeCourse;
import com.example.stockifybackend.Entities.Recette;
import com.example.stockifybackend.Entities.Stock;
import com.example.stockifybackend.Entities.Utilisateur;
import com.example.stockifybackend.Repositories.ListeCourseRepository;
import com.example.stockifybackend.Repositories.RecetteRepository;
import com.example.stockifybackend.Repositories.StockRepository;
import com.example.stockifybackend.Repositories.UtilisateurRepository;
import com.example.stockifybackend.dto.UtilisateurUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ListeCourseRepository listeCourseRepository;

    @Autowired
    private RecetteRepository recetteRepository;

    // add an utilisateur
    public Utilisateur addUtilisateur(Utilisateur utilisateur) {

        Stock stock = new Stock();
        stock = stockRepository.save(stock);
        utilisateur.setStock(stock);



        ListeCourse listeCourse = new ListeCourse();
        listeCourse = listeCourseRepository.save(listeCourse);
        utilisateur.setListeDeCourse_id(listeCourse.getId());

        Utilisateur t = utilisateurRepository.save(utilisateur);

        stock.setUtilisateur(t);
        stockRepository.save(stock);

        return t;
    }

    // update an utilisateur
    public void updateUtilisateurFields(Long id, UtilisateurUpdateRequest updatedUtilisateur) throws ParseException {
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
                existingUtilisateur.setDateDeNaissance(updatedUtilisateur.getDateDeNaissance());
            } catch (ParseException e) {

                e.printStackTrace();
            }
        }

        // Save the updated utilisateur
        utilisateurRepository.save(existingUtilisateur);
    }

    public void updateUtilisateurFieldsSexe(Long id, String nouveauGenre) {
        // Recherche de l'utilisateur par son ID
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(id);

        // Vérification si l'utilisateur existe
        if (optionalUtilisateur.isEmpty()) {
            throw new RuntimeException("Utilisateur with id " + id + " not found");
        }

        // Récupération de l'utilisateur existant
        Utilisateur existingUtilisateur = optionalUtilisateur.get();

        // Mise à jour du genre de l'utilisateur
        existingUtilisateur.setSexe(nouveauGenre);

        // Enregistrement de l'utilisateur mis à jour
        utilisateurRepository.save(existingUtilisateur);
    }

    public void updateUtilisateurFieldsDate(Long id, Date date) {
        // Recherche de l'utilisateur par son ID
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(id);

        // Vérification si l'utilisateur existe
        if (optionalUtilisateur.isEmpty()) {
            throw new RuntimeException("Utilisateur with id " + id + " not found");
        }

        // Récupération de l'utilisateur existant
        Utilisateur existingUtilisateur = optionalUtilisateur.get();

        // Mise à jour du genre de l'utilisateur
        existingUtilisateur.setDateDeNaissance(date);

        // Enregistrement de l'utilisateur mis à jour
        utilisateurRepository.save(existingUtilisateur);
    }

    public void updateUtilisateurFieldsTaille(Long id, String  taille) {
        // Recherche de l'utilisateur par son ID
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(id);

        // Vérification si l'utilisateur existe
        if (optionalUtilisateur.isEmpty()) {
            throw new RuntimeException("Utilisateur with id " + id + " not found");
        }

        // Récupération de l'utilisateur existant
        Utilisateur existingUtilisateur = optionalUtilisateur.get();


        // Mise à jour du genre de l'utilisateur
        existingUtilisateur.setTaille(taille);

        // Enregistrement de l'utilisateur mis à jour
        utilisateurRepository.save(existingUtilisateur);
    }

    public void updateUtilisateurFieldsPoids(Long id, String poids) {
        // Recherche de l'utilisateur par son ID
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(id);

        // Vérification si l'utilisateur existe
        if (optionalUtilisateur.isEmpty()) {
            throw new RuntimeException("Utilisateur with id " + id + " not found");
        }

        // Récupération de l'utilisateur existant
        Utilisateur existingUtilisateur = optionalUtilisateur.get();


        // Mise à jour du genre de l'utilisateur
        existingUtilisateur.setPoids(poids);

        // Enregistrement de l'utilisateur mis à jour
        utilisateurRepository.save(existingUtilisateur);
    }

    public void updateUtilisateurFieldsRegime(Long id, String regime) {
        // Recherche de l'utilisateur par son ID
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(id);

        // Vérification si l'utilisateur existe
        if (optionalUtilisateur.isEmpty()) {
            throw new RuntimeException("Utilisateur with id " + id + " not found");
        }

        // Récupération de l'utilisateur existant
        Utilisateur existingUtilisateur = optionalUtilisateur.get();


        // Mise à jour du genre de l'utilisateur
        existingUtilisateur.setRégimeSpécieux(regime);

        // Enregistrement de l'utilisateur mis à jour
        utilisateurRepository.save(existingUtilisateur);
    }


  //  public void updateUtilisateurFieldsUniteTaille(Long id, String taille) {
        // Recherche de l'utilisateur par son ID
       // Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(id);

        // Vérification si l'utilisateur existe
       // if (optionalUtilisateur.isEmpty()) {
        //    throw new RuntimeException("Utilisateur with id " + id + " not found");
      //  }

        // Récupération de l'utilisateur existant
       // Utilisateur existingUtilisateur = optionalUtilisateur.get();


        // Mise à jour du genre de l'utilisateur
     //   existingUtilisateur.setUnitetaille(taille);

        // Enregistrement de l'utilisateur mis à jour
   //     utilisateurRepository.save(existingUtilisateur);
 //   }

  //  public void updateUtilisateurFieldsUnitePoids(Long id, String taille) {
        // Recherche de l'utilisateur par son ID
       // Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(id);

        // Vérification si l'utilisateur existe
     //   if (optionalUtilisateur.isEmpty()) {
      //      throw new RuntimeException("Utilisateur with id " + id + " not found");
    //    }

        // Récupération de l'utilisateur existant
    //    Utilisateur existingUtilisateur = optionalUtilisateur.get();


        // Mise à jour du genre de l'utilisateur
      //  existingUtilisateur.setUnitepoids(taille);

        // Enregistrement de l'utilisateur mis à jour
    //    utilisateurRepository.save(existingUtilisateur);
  //  }



    public void updateUtilisateurFieldsMode(Long id, Boolean regime) {
        // Recherche de l'utilisateur par son ID
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(id);

        // Vérification si l'utilisateur existe
        if (optionalUtilisateur.isEmpty()) {
            throw new RuntimeException("Utilisateur with id " + id + " not found");
        }

        // Récupération de l'utilisateur existant
        Utilisateur existingUtilisateur = optionalUtilisateur.get();


        // Mise à jour du genre de l'utilisateur
        existingUtilisateur.setModeSportif(regime);

        // Enregistrement de l'utilisateur mis à jour
        utilisateurRepository.save(existingUtilisateur);
    }

    public void updateUtilisateurFieldsAlerteExp(Long id, Boolean regime) {
        // Recherche de l'utilisateur par son ID
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(id);

        // Vérification si l'utilisateur existe
        if (optionalUtilisateur.isEmpty()) {
            throw new RuntimeException("Utilisateur with id " + id + " not found");
        }

        // Récupération de l'utilisateur existant
        Utilisateur existingUtilisateur = optionalUtilisateur.get();


        // Mise à jour du genre de l'utilisateur
        existingUtilisateur.setAlertedateexpi(regime);

        // Enregistrement de l'utilisateur mis à jour
        utilisateurRepository.save(existingUtilisateur);
    }

    public void updateUtilisateurFieldsAlerteQuantiteCr(Long id, Boolean regime) {
        // Recherche de l'utilisateur par son ID
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(id);

        // Vérification si l'utilisateur existe
        if (optionalUtilisateur.isEmpty()) {
            throw new RuntimeException("Utilisateur with id " + id + " not found");
        }

        // Récupération de l'utilisateur existant
        Utilisateur existingUtilisateur = optionalUtilisateur.get();



        existingUtilisateur.setAlerteproduitfinis(regime);


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

    public void AddRecetteAuFavoris(Long user_id, Long recette_id) {
        Optional<Utilisateur> tempUtilisateur = utilisateurRepository.findById(user_id);
        if (tempUtilisateur.isEmpty()) {
            throw new RuntimeException("Utilisateur with id {\"+ id +\"} not found");
        }
        Utilisateur utilisateur = tempUtilisateur.get();
        Optional<Recette> tempRecette = recetteRepository.findById(recette_id);
        if (tempUtilisateur.isEmpty()) {
            throw new RuntimeException("Recette with id {\"+ id +\"} not found");
        }

        Recette recette = tempRecette.get();
        List<Recette> recetteFavoris = utilisateur.getRecettesFavoris();
        recetteFavoris.add(recette);

        utilisateurRepository.save(utilisateur);
    }

    public void removeRecetteAuFavoris(Long user_id, Long recette_id) {
        Optional<Utilisateur> tempUtilisateur = utilisateurRepository.findById(user_id);
        if (tempUtilisateur.isEmpty()) {
            throw new RuntimeException("Utilisateur with id {\"+ id +\"} not found");
        }
        Utilisateur utilisateur = tempUtilisateur.get();
        Optional<Recette> tempRecette = recetteRepository.findById(recette_id);
        if (tempUtilisateur.isEmpty()) {
            throw new RuntimeException("Recette with id {\"+ id +\"} not found");
        }

        Recette recette = tempRecette.get();
        List<Recette> recetteFavoris = utilisateur.getRecettesFavoris();
        recetteFavoris.remove(recette);

        utilisateurRepository.save(utilisateur);

    }
}
