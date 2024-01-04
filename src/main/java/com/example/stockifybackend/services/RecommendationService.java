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

    private RecetteResponse createRecetteResponse(Utilisateur utilisateur, List<Repas> recettesAuStock, List<Produit> produitsAuStock, Recette recette) {
        RecetteResponse recetteResponse = new RecetteResponse(recette);
        recetteResponse.setQuantiteEnStock(recettesAuStock);
        recetteResponse.setIngredients(recette.getIngredients(), produitsAuStock);
        recetteResponse.setNombreIngredientManquantes();
        recetteResponse.setIsFavoris(utilisateur);
        return recetteResponse;
    }

    private List<RecetteResponse> processRecommendationResponse(JSONObject jsonResponse, LocalDateTime tempsDuClient, List<Produit> produitsAuStock, List<Repas> recettesAuStock, Utilisateur utilisateur) throws JSONException {
        if (jsonResponse != null && jsonResponse.has("output")) {
            JSONObject repasProgramme = jsonResponse.getJSONObject("output").getJSONObject("Repas_Programme");

            List<RecetteResponse> recommendedRecettes = new ArrayList<>();

            String repasType = determineRepasType(tempsDuClient);

            JSONArray recettesArray = repasProgramme.getJSONArray(repasType);

            for (int i = 0; i < recettesArray.length(); i++) {
                JSONObject recetteObject = recettesArray.getJSONObject(i);

                // Extraire les informations de la recette et créer un objet Recette
                Long recetteId = recetteObject.getLong("Recipe_Id");
                Optional<Recette> optionalRecette = recetteRepository.findById(recetteId);

                optionalRecette.ifPresent(recette -> {
                    RecetteResponse recetteResponse = createRecetteResponse(utilisateur, recettesAuStock, produitsAuStock, recette);
                    recommendedRecettes.add(recetteResponse);
                });
            }

            return recommendedRecettes;
        }

        return new ArrayList<>();
    }

    public List<RecetteResponse> getRecommendedRecettes(long userId, LocalDateTime tempsDuClient) throws JSONException {
        String url = recommendationSystemUrl + "/Repas_suggestions/";
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(userId);

        Utilisateur utilisateur = optionalUtilisateur.orElseThrow(() -> new RuntimeException("Utilisateur with id " + userId + " not found"));

        List<Produit> produitsAuStock = stockService.getAllProductsInStock(utilisateur.getStock_id());
        List<Repas> recettesAuStock = stockService.getAllRecettesInStock(utilisateur.getStock_id());

        String requestJson = buildRecommendationRequestJson(utilisateur);

        JSONObject jsonResponse = sendRecommendationRequest(requestJson, url);

        return processRecommendationResponse(jsonResponse, tempsDuClient, produitsAuStock, recettesAuStock, utilisateur);
    }

    private boolean hasPreferredIngredients(Recette recette, List<String> preferredIngredients) {
        if(preferredIngredients.isEmpty()) {
            return true;
        }
        List<String> ingredientNames = recette.getIngredients().stream()
                .map(Ingredient::getIntitule)
                .collect(Collectors.toList());

        return ingredientNames.containsAll(preferredIngredients);
    }

    private boolean isRecetteValid(Recette recette, String régimeSpéciale, String tempsDePreparation, List<String> nomsDesIngrédientPréféres) {
        if(régimeSpéciale.isEmpty() || tempsDePreparation.isEmpty()){
            return true;
        }
        int totalTimeMinutes = recette.getDureeTotal();
        String categorieDeRecette = recette.getCategorieDeRecette().getIntitule();

        boolean result = categorieDeRecette.equals(régimeSpéciale) && totalTimeMinutes <= Integer.parseInt(tempsDePreparation) && hasPreferredIngredients(recette, nomsDesIngrédientPréféres);

        return categorieDeRecette.equals(régimeSpéciale) &&
                totalTimeMinutes <= Integer.parseInt(tempsDePreparation) &&
                hasPreferredIngredients(recette, nomsDesIngrédientPréféres);
    }

    private RecetteResponse processRecetteObject(JSONObject recetteObject, List<Repas> recettesAuStock, List<Produit> produitsAuStock, String régimeSpéciale, String tempsDePreparation, List<String> nomsDesIngrédientPréféres, Utilisateur utilisateur) throws JSONException {
        Long recetteId = recetteObject.getLong("Recipe_Id");
        return recetteRepository.findById(recetteId)
                .filter(recette -> isRecetteValid(recette, régimeSpéciale, tempsDePreparation, nomsDesIngrédientPréféres))
                .map(recette -> createRecetteResponse(utilisateur, recettesAuStock, produitsAuStock, recette))
                .orElse(null);
    }

    private List<RecetteResponse> processRecommendationFiltredResponse(JSONObject jsonResponse, LocalDateTime tempsDuClient, List<Produit> produitsAuStock, List<Repas> recettesAuStock,String régimeSpéciale, String tempsDePreparation, List<String> nomsDesIngrédientPréféres, Utilisateur utilisateur) throws JSONException {
        if (jsonResponse != null && jsonResponse.has("output")) {
            JSONObject repasProgramme = jsonResponse.getJSONObject("output").getJSONObject("Repas_Programme");

            List<RecetteResponse> recommendedFiltredRecettes = new ArrayList<>();

            String repasType = determineRepasType(tempsDuClient);

            JSONArray recettesArray = repasProgramme.getJSONArray(repasType);


            for (int i = 0; i < recettesArray.length(); i++) {
                JSONObject recetteObject = recettesArray.getJSONObject(i);

                RecetteResponse recetteResponse = processRecetteObject(recetteObject, recettesAuStock, produitsAuStock, régimeSpéciale, tempsDePreparation, nomsDesIngrédientPréféres, utilisateur);

                if(recetteResponse!= null){
                    recommendedFiltredRecettes.add(recetteResponse);
                }
            }

            return recommendedFiltredRecettes;
        }

        return new ArrayList<>();
    }

    /* ---------------------------------------------------------*/


    public void setRecommendationSystemUrl(String recommendationSystemUrl) {
        this.recommendationSystemUrl = recommendationSystemUrl;
    }
}
