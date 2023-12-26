package com.example.stockifybackend.services;

import com.example.stockifybackend.Entities.Ingredient;
import com.example.stockifybackend.Entities.Recette;
import com.example.stockifybackend.Entities.Recommendation;
import com.example.stockifybackend.Entities.Utilisateur;
import com.example.stockifybackend.Repositories.RecetteRepository;
import com.example.stockifybackend.Repositories.RecommendationRepository;
import com.example.stockifybackend.Repositories.UtilisateurRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    private final UtilisateurRepository utilisateurRepository;

    private final RecetteRepository recetteRepository;

    @Value("${recommendation.system.url}")
    private String recommendationSystemUrl;

    @Autowired
    public RecommendationService(RecommendationRepository recommendationRepository, UtilisateurRepository utilisateurRepository, RecetteRepository recetteRepository) {
        this.recommendationRepository = recommendationRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.recetteRepository = recetteRepository;
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

// ...

    public List<Recette> getRecommendedRecettes(long user_id, LocalDateTime tempsDuClient) throws JSONException {
        String url = recommendationSystemUrl + "/Repas_suggestions/";

        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(user_id);

        if (optionalUtilisateur.isEmpty()) {
            throw new RuntimeException("Utilisateur with id " + user_id + " not found");
        }

        Utilisateur utilisateur = optionalUtilisateur.get();

        // Calcul de l'âge à partir de la date de naissance
        LocalDate dateDeNaissance = utilisateur.getDateDeNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate maintenant = LocalDate.now();
        int age = Period.between(dateDeNaissance, maintenant).getYears();

        int taille = Integer.parseInt(utilisateur.getTaille());
        int poids = Integer.parseInt(utilisateur.getPoids());
        String sexe = "male";
        if (utilisateur.getSexe().equals("Femme")) {
            sexe = "female";
        }
        String weight_loss_plan = "Maintain weight";
        if (utilisateur.isModeSportif()) {
            weight_loss_plan = "Mild weight loss";
        }

        // Construire la chaîne JSON directement
        String requestJson = "{" +
                "\"age\":" + age + "," +
                "\"height\":" + taille + "," +
                "\"weight\":" + poids + "," +
                "\"gender\":\"" + sexe + "\"," +
                "\"activity\":\"Little/no exercise\"," +
                "\"number_of_meals\":3," +
                "\"weight_loss_plan\":\"" + weight_loss_plan + "\"" +
                "}";

        JSONObject jsonResponse = sendRecommendationRequest(url, requestJson);

        if (jsonResponse != null && jsonResponse.has("output")) {
            JSONObject repasProgramme = jsonResponse.getJSONObject("output").getJSONObject("Repas_Programme");

            List<Recette> recommendedRecettes = new ArrayList<>();

            String repasType;

            // Logique pour déterminer le repas en fonction du temps du client
            if (tempsDuClient.getHour() < 12) {
                repasType = "breakfast";
            } else if (tempsDuClient.getHour() < 18) {
                repasType = "lunch";
            } else {
                repasType = "dinner";
            }

            JSONArray recettesArray = repasProgramme.getJSONArray(repasType);

            for (int i = 0; i < recettesArray.length(); i++) {
                JSONObject recetteObject = recettesArray.getJSONObject(i);

                // Extraire les informations de la recette et créer un objet Recette
                Long recetteId = recetteObject.getLong("Recipe_Id");
                Recette recette = new Recette(); recette.setId(recetteId);
                recommendedRecettes.add(recette);
                /*Optional<Recette> optionalRecette = recetteRepository.findById(recetteId);

                 if (optionalRecette.isPresent()) {
                    recommendedRecettes.add(optionalRecette.get());
                 }*/
            }

            return recommendedRecettes;
        }

        return new ArrayList<>();
    }

    private JSONObject sendRecommendationRequest(String url, String requestJson) throws JSONException {
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


    private int extractMinutesFromTimeString(String timeString) {
        // Vérifier si la chaîne est non nulle et non vide
        if (timeString != null && !timeString.isEmpty()) {
            // Séparer la partie numérique de la chaîne
            String[] parts = timeString.split("\\s+");

            // Vérifier s'il y a deux parties (ex. "45 min")
            if (parts.length == 2) {
                try {
                    // Extraire le nombre de la première partie
                    int minutes = Integer.parseInt(parts[0]);

                    // Vérifier si la deuxième partie contient "min"
                    if ("min".equalsIgnoreCase(parts[1])) {
                        return minutes;
                    }
                } catch (NumberFormatException e) {
                    // Gérer l'exception si la conversion en entier échoue
                    e.printStackTrace();
                }
            }
        }

        // Retourner une valeur par défaut si la chaîne ne correspond pas au format attendu
        return -1;
    }

    private boolean hasPreferredIngredients(JSONObject recetteObject, List<String> preferredIngredients) throws JSONException {
        // Vérifier si la recette contient tous les ingrédients préférés
        JSONArray ingrédientsArray = recetteObject.getJSONArray("RecipeIngredients");

        for (int j = 0; j < ingrédientsArray.length(); j++) {
            String ingrédient = ingrédientsArray.getString(j);
            if (!preferredIngredients.contains(ingrédient)) {
                return false;
            }
        }

        return true;
    }

    public List<Recette> getRecommendedFilteredRecettes(long user_id, LocalDateTime tempsDuClient, String régimeSpéciale, String tempsDePreparation, List<String> nomsDesIngrédientPréféres) throws JSONException {
        String url = recommendationSystemUrl + "/Repas_suggestions/";

        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(user_id);

        if (optionalUtilisateur.isEmpty()) {
            throw new RuntimeException("Utilisateur with id " + user_id + " not found");
        }

        Utilisateur utilisateur = optionalUtilisateur.get();

        // Calcul de l'âge à partir de la date de naissance
        LocalDate dateDeNaissance = utilisateur.getDateDeNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate maintenant = LocalDate.now();
        int age = Period.between(dateDeNaissance, maintenant).getYears();

        int taille = Integer.parseInt(utilisateur.getTaille());
        int poids = Integer.parseInt(utilisateur.getPoids());

        String sexe = "male";
        if (utilisateur.getSexe().equals("Femme")) {
            sexe = "female";
        }
        String weight_loss_plan = "Maintain weight";
        if (utilisateur.isModeSportif()) {
            weight_loss_plan = "Mild weight loss";
        }

        // Construire la chaîne JSON directement
        String requestJson = "{" +
                "\"age\":" + age + "," +
                "\"height\":" + taille + "," +
                "\"weight\":" + poids + "," +
                "\"gender\":\"" + sexe + "\"," +
                "\"activity\":\"Little/no exercise\"," +
                "\"number_of_meals\":3," +
                "\"weight_loss_plan\":\"" + weight_loss_plan + "\"" +
                "}";

        JSONObject jsonResponse = sendRecommendationRequest(url, requestJson);

        if (jsonResponse != null && jsonResponse.has("output")) {
            JSONObject repasProgramme = jsonResponse.getJSONObject("output").getJSONObject("Repas_Programme");

            List<Recette> recommendedRecettes = new ArrayList<>();

            String repasType;

            // Logique pour déterminer le repas en fonction du temps du client
            if (tempsDuClient.getHour() < 12) {
                repasType = "breakfast";
            } else if (tempsDuClient.getHour() < 18) {
                repasType = "lunch";
            } else {
                repasType = "dinner";
            }

            JSONArray recettesArray = repasProgramme.getJSONArray(repasType);

            for (int i = 0; i < recettesArray.length(); i++) {
                JSONObject recetteObject = recettesArray.getJSONObject(i);

                int totalTimeMinutes = extractMinutesFromTimeString((String) recetteObject.get("TotalTime"));

                /*Optional<Recette> optionalRecette = recetteRepository.findById(recetteId);

                if (optionalRecette.isPresent()) {
                    Recette recette = optionalRecette.get();
                    recommendedRecettes.add(recette);

                }*/

                //if (recette.get("categorieDeRecette").equals(régimeSpéciale) &&
                if(totalTimeMinutes <= Integer.parseInt(tempsDePreparation) && hasPreferredIngredients(recetteObject, nomsDesIngrédientPréféres))
                {
                    // Extraire les informations de la recette et créer un objet Recette
                    Long recetteId = recetteObject.getLong("Recipe_Id");
                    String recetteName = recetteObject.getString("Recipe_Name");
                    Recette recette = new Recette();
                    recette.setId(recetteId);
                    recette.setIntitule(recetteName);
                    recette.setDureeTotal(totalTimeMinutes);
                    recommendedRecettes.add(recette);

                    }
                }

            return recommendedRecettes;
        }

        return new ArrayList<>();
    }


    public List<Recette> getRecettesSimilaires(long reccette_id) throws JSONException {
        String url = recommendationSystemUrl + "/Recipe_suggestions/";

        /*Optional<Recette> optionalRecette = recetteRepository.findById(reccette_id);

        if (optionalRecette.isEmpty()) {
            throw new RuntimeException("Reccette with id " + reccette_id + " not found");
        }

        Recette existingRecette = optionalRecette.get();

        List<String> ingrédientsNames = new ArrayList<>();

        for (Ingredient ingrédient : existingRecette.getIngredients()) {
            ingrédientsNames.add(ingrédient.getIntitule());
        }*/

        List<String> ingrédientsNames = new ArrayList<>(List.of("\"Egg\"", "\"Tomato\""));

        // Construire la chaîne JSON directement
        String requestJson = "{" +
                "\"nutrition_input\": [500, 50, 0, 0, 400, 100, 10, 10, 10]," +
                "\"number_of_recommendations\": 3, " +
                "\"ingredients\": " + ingrédientsNames +
                "}";

        JSONObject jsonResponse = sendRecommendationRequest(url, requestJson);

        if (jsonResponse != null && jsonResponse.has("output")) {
            JSONArray recettesArray = jsonResponse.getJSONArray("output");

            List<Recette> similarRecettes = new ArrayList<>();

            for (int i = 0; i < recettesArray.length(); i++) {
                /*Optional<Recette> optionalRecette = recetteRepository.findById(recetteId);

                 if (optionalRecette.isPresent()) {
                    recommendedRecettes.add(optionalRecette.get());
                 }*/

                JSONObject recetteObject = recettesArray.getJSONObject(i);

                // Extraire les informations de la recette et créer un objet Recette
                Long recetteId = recetteObject.getLong("Recipe_Id");
                Recette recette = new Recette(); recette.setId(recetteId);
                similarRecettes.add(recette);
            }

            return similarRecettes;
        }

        return new ArrayList<>();
    }
}
