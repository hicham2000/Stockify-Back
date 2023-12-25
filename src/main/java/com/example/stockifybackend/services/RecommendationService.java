package com.example.stockifybackend.services;

import com.example.stockifybackend.Entities.Recette;
import com.example.stockifybackend.Entities.Recommendation;
import com.example.stockifybackend.Repositories.RecommendationRepository;
import com.example.stockifybackend.Repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public RecommendationService(RecommendationRepository recommendationRepository, UtilisateurRepository utilisateurRepository) {
        this.recommendationRepository = recommendationRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<Recommendation> getAllRecommendations() {
        return recommendationRepository.findAll();
    }

    public Optional<Recommendation> getRecommendationById(Long id) {
        return recommendationRepository.findById(id);
    }

    public Recommendation saveRecommendation(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
    }

    public void deleteRecommendation(Long id) {
        recommendationRepository.deleteById(id);
    }

    public List<Recette> getRecommendedRecettes(long user_id){

    }

    public List<Recette> getRecommendedFilteredRecettes(long user_id, String régimeSpéciale, String tempsDePreparation, List<String> nomsDesIngrédientPréféres){

    }

    public List<Recette> getRecettesSimilaires(long reccette_id){

    }
}
