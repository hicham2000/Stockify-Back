package com.example.stockifybackend.services;

import com.example.stockifybackend.Entities.*;
import com.example.stockifybackend.Repositories.RecetteRepository;
import com.example.stockifybackend.Repositories.UtilisateurRepository;
import com.example.stockifybackend.dto.RecetteResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class RecommendationService {

    private final UtilisateurRepository utilisateurRepository;

    private final RecetteRepository recetteRepository;
    private final StockService stockService;

    @Value("${recommendation.system.url}")
    private String recommendationSystemUrl;

    @Autowired
    public RecommendationService(UtilisateurRepository utilisateurRepository, RecetteRepository recetteRepository, StockService stockService) {
        this.utilisateurRepository = utilisateurRepository;
        this.recetteRepository = recetteRepository;
        this.stockService = stockService;
    }

    private int calculateAge(Date dateDeNaissance) {

        LocalDate birthDate = dateDeNaissance.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        return Period.between(birthDate, now).getYears();
    }

    private String buildRecommendationRequestJson(Utilisateur utilisateur, LocalDateTime tempsDuClient) {
        int age = calculateAge(utilisateur.getDateDeNaissance());
        int taille = Integer.parseInt(utilisateur.getTaille());
        int poids = Integer.parseInt(utilisateur.getPoids());
        String sexe = utilisateur.getSexe().equals("Femme") ? "female" : "male";
        String weight_loss_plan = utilisateur.isModeSportif() ? "Mild weight loss" : "Maintain weight";

        return String.format(
                "{" +
                        "\"age\": %d," +
                        "\"height\": %d," +
                        "\"weight\": %d," +
                        "\"gender\": \"%s\"," +
                        "\"activity\": \"Little/no exercise\"," +
                        "\"number_of_meals\": 3," +
                        "\"weight_loss_plan\": \"%s\"" +
                        "}",
                age, taille, poids, sexe, weight_loss_plan
        );
    }



    public void setRecommendationSystemUrl(String recommendationSystemUrl) {
        this.recommendationSystemUrl = recommendationSystemUrl;
    }
}
