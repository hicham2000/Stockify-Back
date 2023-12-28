package com.example.stockifybackend.services;

import com.example.stockifybackend.Entities.*;
import com.example.stockifybackend.Repositories.RecetteRepository;
import com.example.stockifybackend.Repositories.UtilisateurRepository;
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
import java.util.List;
import java.util.Optional;
@Service
public class RecommendationService {

    private final UtilisateurRepository utilisateurRepository;

    private final RecetteRepository recetteRepository;

    @Value("${recommendation.system.url}")
    private String recommendationSystemUrl;

    @Autowired
    public RecommendationService(UtilisateurRepository utilisateurRepository, RecetteRepository recetteRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.recetteRepository = recetteRepository;
    }


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
                Optional<Recette> optionalRecette = recetteRepository.findById(recetteId);

                 if (optionalRecette.isPresent()) {
                    Recette recette = optionalRecette.get();
                    recommendedRecettes.add(recette);
                 }
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


    private boolean hasPreferredIngredients(Recette recette, List<String> preferredIngredients) throws JSONException {
        // Vérifier si la recette contient tous les ingrédients préférés
        List<Ingredient> ingrédients = recette.getIngredients();

        for (Ingredient ingrédient: ingrédients) {
            String ingrédientName = ingrédient.getIntitule();
            if (!preferredIngredients.contains(ingrédientName)) {
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

                Long recetteId = recetteObject.getLong("Recipe_Id");

                Optional<Recette> optionalRecette = recetteRepository.findById(recetteId);

                if (optionalRecette.isPresent()) {
                    Recette recette = optionalRecette.get();

                    int totalTimeMinutes = recette.getDureeTotal();
                    String categorieDeRecette = recette.getCategorieDeRecette().getIntitule();

                    if (categorieDeRecette.equals(régimeSpéciale) &&
                            totalTimeMinutes <= Integer.parseInt(tempsDePreparation) &&
                            hasPreferredIngredients(recette, nomsDesIngrédientPréféres))
                    {
                        recommendedRecettes.add(recette);
                    }

                }

            }

            return recommendedRecettes;
        }

        return new ArrayList<>();
    }


    public List<Recette> getRecettesSimilaires(long recette_id) throws JSONException {
        Logger logger = LoggerFactory.getLogger(getClass());
        String url = recommendationSystemUrl + "/Recipe_suggestions/";

        Optional<Recette> optionalRecette = recetteRepository.findById(recette_id);

        if (optionalRecette.isEmpty()) {
            throw new RuntimeException("Reccette with id " + recette_id + " not found");
        }

        Recette existingRecette = optionalRecette.get();

        List<String> ingrédientsNames = new ArrayList<>();

        int count =0;
        for (Ingredient ingrédient : existingRecette.getIngredients()) {
            if(count == 2){
                break;
            }
            ingrédientsNames.add("\"" +ingrédient.getIntitule() + "\"");
            count++;
        }

        ValeurNutritionnel recetteValeurNutritionnel = existingRecette.getValeurNutritionnel();

        List<Double> valeurNutritionnnelValues = new ArrayList<>(List.of(
                recetteValeurNutritionnel.getEnegie(),      //Calories
                recetteValeurNutritionnel.getLipide(),      //FatContent
                0.0,                                          //SaturatedFatContent
                0.0,                                          //CholesterolContent
                0.0,                                          //SodiumContent
                recetteValeurNutritionnel.getCarbohydrate(),//CarbohydrateContent
                recetteValeurNutritionnel.getFibre(),       //FiberContent
                recetteValeurNutritionnel.getSucre(),       //SugarContent
                recetteValeurNutritionnel.getProteine()     //ProteinContent
                ));

        // Construire la chaîne JSON directement
        String requestJson = "{" +
                "\"nutrition_input\": "+ valeurNutritionnnelValues + "," +
                "\"number_of_recommendations\": 3, " +
                "\"ingredients\": " + ingrédientsNames +
                "}";

        JSONObject jsonResponse = sendRecommendationRequest(url, requestJson);

        if (jsonResponse != null && jsonResponse.has("output")) {
            JSONArray recettesArray = jsonResponse.getJSONArray("output");

            //System.out.println("recettesArray  => " + recettesArray);
            logger.debug("recettesArray  => {}", recettesArray);

            List<Recette> similarRecettes = new ArrayList<>();

            for (int i = 0; i < recettesArray.length(); i++) {

                JSONObject recetteObject = recettesArray.getJSONObject(i);

                // Extraire les informations de la recette et créer un objet Recette
                Long recetteId = recetteObject.getLong("Recipe_Id");

                optionalRecette = recetteRepository.findById(recetteId);

                if (optionalRecette.isPresent()) {
                    Recette recette = optionalRecette.get();
                    logger.debug("recette {}: => {}", i, recette);
                    //System.out.println("recette "+i+": => "+ recette);
                    similarRecettes.add(recette);
                }
            }

            return similarRecettes;
        }

        return new ArrayList<>();
    }
}
