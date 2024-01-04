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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RecommendationService {

    private final UtilisateurRepository utilisateurRepository;

    private final RecetteRepository recetteRepository;
    private final StockService stockService;
    private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);


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

    private String buildRecommendationRequestJson(Utilisateur utilisateur) {
        int age = calculateAge(utilisateur.getDateDeNaissance());
        int taille = Integer.parseInt(utilisateur.getTaille());
        int poids = Integer.parseInt(utilisateur.getPoids());
        String sexe = utilisateur.getSexe().equalsIgnoreCase("Femme") ? "female" : "male";
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

    private JSONObject sendRecommendationRequest(String requestJson, String url) throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity;

        try {
            responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }

        if (responseEntity.getBody() == null) {
            return null;
        }

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return new JSONObject(responseEntity.getBody());
        }

        return null;
    }

    private String determineRepasType(LocalDateTime tempsDuClient) {
        if (tempsDuClient.getHour() < 12) {
            return "breakfast";
        } else if (tempsDuClient.getHour() < 18) {
            return "lunch";
        } else {
            return "dinner";
        }
    }

    /* ---------------------------------------------------------*/


    public void setRecommendationSystemUrl(String recommendationSystemUrl) {
        this.recommendationSystemUrl = recommendationSystemUrl;
    }
}
