package com.example.stockifybackend.ServiceTest;

import com.example.stockifybackend.Entities.*;
import com.example.stockifybackend.Repositories.RecetteRepository;
import com.example.stockifybackend.Repositories.UtilisateurRepository;
import com.example.stockifybackend.dto.RecetteResponse;
import com.example.stockifybackend.services.RecommendationService;
import com.example.stockifybackend.services.StockService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class RecommendationServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private RecetteRepository recetteRepository;

    @Mock
    private StockService stockService;
    @Mock
    private Utilisateur utilisateur;
    private static final String RECOMMENDATION_SYSTEM_URL = "http://localhost:8081";

    @InjectMocks
    private RecommendationService recommendationService;


    @BeforeEach
    public void setUp() throws ParseException {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCalculateAge() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date dateOfBirth = formatter.parse("09-12-2001");
        int age = recommendationService.calculateAge(dateOfBirth);
        assertEquals(22, age);
    }

    @Test
    void testBuildRecommendationRequestJson() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date dateDeNaissance = formatter.parse("09-12-2001");

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setDateDeNaissance(dateDeNaissance);
        utilisateur.setTaille("179");
        utilisateur.setPoids("62");
        utilisateur.setSexe("Homme");
        utilisateur.setModeSportif(false);

        String requestJson = recommendationService.buildRecommendationRequestJson(utilisateur);

        // Assertions pour vérifier la présence des champs dans la chaîne JSON
        assertTrue(requestJson.contains("\"age\":"));
        assertTrue(requestJson.contains("\"height\":"));
        assertTrue(requestJson.contains("\"weight\":"));
        assertTrue(requestJson.contains("\"gender\":"));
        assertTrue(requestJson.contains("\"activity\":"));
        assertTrue(requestJson.contains("\"number_of_meals\":"));
        assertTrue(requestJson.contains("\"weight_loss_plan\":"));

        // Assertions pour vérifier les types des valeurs dans la chaîne JSON
        assertTrue(requestJson.matches(".*\"age\": \\d+.*"));
        assertTrue(requestJson.matches(".*\"height\": \\d+.*"));
        assertTrue(requestJson.matches(".*\"weight\": \\d+.*"));
        assertTrue(requestJson.matches(".*\"gender\": \".+\".*"));
        assertTrue(requestJson.matches(".*\"activity\": \".+\".*"));
        assertTrue(requestJson.matches(".*\"number_of_meals\": \\d+.*"));
        assertTrue(requestJson.matches(".*\"weight_loss_plan\": \".+\".*"));
    }

    @Test
    void testSendRecommendationRequest_Success() throws JSONException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date dateDeNaissance = formatter.parse("09-12-2001");

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setDateDeNaissance(dateDeNaissance);
        utilisateur.setTaille("179");
        utilisateur.setPoids("62");
        utilisateur.setSexe("Homme");
        utilisateur.setModeSportif(false);

        String requestJson = recommendationService.buildRecommendationRequestJson(utilisateur);

        String url = RECOMMENDATION_SYSTEM_URL + "/Repas_suggestions/";
        JSONObject jsonResponse = recommendationService.sendRecommendationRequest(requestJson, url);

        assertNotNull(jsonResponse);
        assertTrue(jsonResponse.has("Message"));
        assertTrue(jsonResponse.getString("Message").equals("Get recipes successfully"));
        assertTrue(jsonResponse.has("output"));
        assertTrue(jsonResponse.getJSONObject("output").has("Repas_Programme"));
        assertTrue(jsonResponse.getJSONObject("output").getJSONArray("Repas_Programme").length() > 0);
    }

    @Test
    void testSendRecommendationRequest_Failure_404() throws ParseException, JSONException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date dateDeNaissance = formatter.parse("09-12-2001");

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setDateDeNaissance(dateDeNaissance);
        utilisateur.setTaille("179");
        utilisateur.setPoids("62");
        utilisateur.setSexe("Homme");
        utilisateur.setModeSportif(false);

        String requestJson = recommendationService.buildRecommendationRequestJson(utilisateur);

        String url = RECOMMENDATION_SYSTEM_URL + "/Repas_suggestion/";
        JSONObject jsonResponse = recommendationService.sendRecommendationRequest(requestJson, url);

        assertNull(jsonResponse);
    }

    @Test
    void testSendRecommendationRequest_Failure_307() throws ParseException, JSONException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date dateDeNaissance = formatter.parse("09-12-2001");

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setDateDeNaissance(dateDeNaissance);
        utilisateur.setTaille("179");
        utilisateur.setPoids("62");
        utilisateur.setSexe("Homme");
        utilisateur.setModeSportif(false);

        String requestJson = recommendationService.buildRecommendationRequestJson(utilisateur);

        String url = RECOMMENDATION_SYSTEM_URL + "/Repas_suggestions";
        JSONObject jsonResponse = recommendationService.sendRecommendationRequest(requestJson, url);

        assertNull(jsonResponse);
    }

    @Test
    void testSendRecommendationRequest_Failure_Host_Invalid() throws ParseException, JSONException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date dateDeNaissance = formatter.parse("09-12-2001");

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setDateDeNaissance(dateDeNaissance);
        utilisateur.setTaille("179");
        utilisateur.setPoids("62");
        utilisateur.setSexe("Homme");
        utilisateur.setModeSportif(false);

        String requestJson = recommendationService.buildRecommendationRequestJson(utilisateur);

        String url = RECOMMENDATION_SYSTEM_URL.replace("8081", "8080") + "/Repas_suggestions/";
        JSONObject jsonResponse = recommendationService.sendRecommendationRequest(requestJson, url);

        assertNull(jsonResponse);
    }

    @Test
    void testSendRecommendationRequest_Failure_Request_Body_Invalid() throws ParseException, JSONException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date dateDeNaissance = formatter.parse("09-12-2001");

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setDateDeNaissance(dateDeNaissance);
        utilisateur.setTaille("179");
        utilisateur.setPoids("62");
        utilisateur.setSexe("Homme");
        utilisateur.setModeSportif(false);

        String requestJson = recommendationService.buildRecommendationRequestJson(utilisateur) + "}";

        String url = RECOMMENDATION_SYSTEM_URL+ "/Repas_suggestions/";
        JSONObject jsonResponse = recommendationService.sendRecommendationRequest(requestJson, url);

        assertNull(jsonResponse);
    }

    @Test
    void testCreateRecetteResponse() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date dateDeNaissance = formatter.parse("09-12-2001");

        //Créer Utilisateur
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setDateDeNaissance(dateDeNaissance);
        utilisateur.setTaille("179");
        utilisateur.setPoids("62");
        utilisateur.setSexe("Homme");
        utilisateur.setModeSportif(false);

        Stock s = new Stock();
        s.setId(1L);
        s.setQuantiteCritiqueParDefaut(190);

        utilisateur.setStock(s);

        //Créer Recette
        Recette recette = new Recette();
        recette.setId(38L);
        recette.setIntitule("Low-Fat Berry Blue Frozen Dessert");
        recette.setDescription("Make and share this Low-Fat Berry Blue Frozen Dessert recipe from Food.com.");
        recette.setDureeTotal(1485);
        recette.setInstructionsDePreparation("[\"Toss 2 ups berries with sugar\", \"Let stand for 45 minute\", \"stirring oasionally\", \"Transfer berry-sugar mixture to food proessor\", \"Add yogurt and proess until smooth\", \"Strain through fine sieve\", \"Pour into baking pan or transfer to ie ream maker and proess aording to manufaturers' diretions\", \"Freeze unovered until edges are solid but entre is soft\", \"Transfer to proessor and blend until smooth again\", \"Return to pan and freeze until edges are solid\", \"Transfer to proessor and blend until smooth again\", \"Fold in remaining 2 ups of blueberries\", \"Pour into plasti mold and freeze overnight\", \"Let soften slightly to serve.\"]");
        recette.setImageUrl("https://img.sndimg.com/food/image/upload/w_555,h_416,c_fit,fl_progressive,q_95/v1/img/recipes/38/YUeirxMLQaeE1h3v3qnM_229%20berry%20blue%20frzn%20dess.jpg");

        //Créer Valeur Nutritionnel de recette
        ValeurNutritionnel valeurNutritionnel = new ValeurNutritionnel();
        valeurNutritionnel.setCarbohydrate(37.1);
        valeurNutritionnel.setEnegie(170.9);
        valeurNutritionnel.setFibre(3.6);
        valeurNutritionnel.setLipide(2.5);
        valeurNutritionnel.setProteine(3.2);
        valeurNutritionnel.setSucre( 30.2);
        recette.setValeurNutritionnel(valeurNutritionnel);

        //Créer Catégorie de recette
        CategorieDeRecette categorieDeRecette = new CategorieDeRecette();
        categorieDeRecette.setIntitule("Frozen Desserts");
        recette.setCategorieDeRecette(categorieDeRecette);

        //Créer un ingredient de recette
        Ingredient ingredient = new Ingredient();
        ingredient.setId(2L);
        ingredient.setIntitule("blueberries");
        ingredient.setQuantity(4.0);
        ingredient.setRecette(recette);
        recette.setIngredients(new ArrayList<>(List.of(ingredient)));

        //Ajouter  la recette au stock et favoris d'utilisateur
        utilisateur.getStock().setRecette(new ArrayList<>(List.of(recette)));
        utilisateur.setRecettesFavoris(new ArrayList<>(List.of(recette)));

        List<Recette> recettesAuStock = utilisateur.getStock().getRecette();

        //Créer un produit et ajouter le dans stock d'utilisateur
        Produit produit = new Produit();
        produit.setId(1L);
        produit.setIntitule("blueberries");
        produit.setQuantite(10.0);

        utilisateur.getStock().setProduit(new ArrayList<>(List.of(produit)));
        List<Produit> produitsAuStock = utilisateur.getStock().getProduit();

        RecetteResponse recetteResponse = recommendationService.createRecetteResponse(utilisateur, recettesAuStock, produitsAuStock, recette);

        // Vérifications pour les champs de base
        assertRecetteResponseBasics(recette, recetteResponse);

        // Vérifications pour la catégorie de recette
        assertCategorieDeRecette(recette.getCategorieDeRecette(), recetteResponse.getCategorieDeRecette());

        // Vérifications pour la valeur nutritionnelle
        assertValeurNutritionnel(recette.getValeurNutritionnel(), recetteResponse.getValeurNutritionnel());

        // Vérifications pour les instructions et les ingrédients
        assertInstructionsAndIngredients(recette, recetteResponse);

        // Vérifications pour les ingrédients manquants
        assertNombreIngredientsManquantes(recetteResponse);

        // Vérification si la recette appartient aux favoris de l'utilisateur
        assertTrue(recetteResponse.isFavoris());
    }

    private void assertRecetteResponseBasics(Recette expected, RecetteResponse actual) {
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getIntitule(), actual.getIntitule());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getDureeTotal(), actual.getDureeTotal());
        assertEquals(expected.getImageUrl(), actual.getImageUrl());
    }

    private void assertCategorieDeRecette(CategorieDeRecette expected, CategorieDeRecette actual) {
        assertNotNull(actual);
        assertEquals(expected.getIntitule(), actual.getIntitule());
    }

    private void assertValeurNutritionnel(ValeurNutritionnel expected, ValeurNutritionnel actual) {
        assertNotNull(actual);
        assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate());
        assertEquals(expected.getEnegie(), actual.getEnegie());
        assertEquals(expected.getFibre(), actual.getFibre());
        assertEquals(expected.getLipide(), actual.getLipide());
        assertEquals(expected.getProteine(), actual.getProteine());
        assertEquals(expected.getSucre(), actual.getSucre());
    }

    private void assertInstructionsAndIngredients(Recette expected, RecetteResponse actual) {
        assertFalse(actual.getInstructionsList().isEmpty());
        assertIterableEquals(expected.getInstructionsList(), actual.getInstructionsList());

        assertFalse(actual.getIngredients().isEmpty());
        assertIterableEquals(expected.getIngredients().stream().map(Ingredient::getId).collect(Collectors.toList()),
                actual.getIngredients().stream().map(RecetteResponse.IngredientInfo::getId).collect(Collectors.toList()));
    }

    private void assertNombreIngredientsManquantes(RecetteResponse actual) {
        assertEquals(0, actual.getNombreIngredientsManquantes());
    }

    @Test
    void testProcessRecommendationResponse() throws ParseException, JSONException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date dateDeNaissance = formatter.parse("09-12-2001");

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setDateDeNaissance(dateDeNaissance);
        utilisateur.setTaille("179");
        utilisateur.setPoids("62");
        utilisateur.setSexe("Homme");
        utilisateur.setModeSportif(false);

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("output", new JSONObject());
        JSONArray repasProgramme = new JSONArray(new JSONTokener("[{" +
                "\"Recipe_Id\": 427097," +
                "\"Recipe_Name\": \"Venison Gyros\"," +
                "\"Recipe_Image_link\": \"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRy-LIBOCdej1jguFE-N1O12SzfNMuYP6y4sc0lS_SOm8SVQAu8-8gcRbOsiI&s\"," +
                "\"Recipe_nutritions_values\": {" + "\"Calories\": 425.9," + "\"FatContent\": 12.7," + "\"SaturatedFatContent\": 3.1," + "\"CholesterolContent\": 28.4," + "\"SodiumContent\": 399.8," + "\"CarbohydrateContent\": 45.2," + "\"FiberContent\": 3.0," + "\"SugarContent\": 7.4," + "\"ProteinContent\": 33.4" + "}," +
                "\"RecipeIngredients\": [" + "\"plain yogurt\"," + "\"dill\"," + "\"garlic powder\"," + "\"cucumber\"," + "\"olive oil\"," + "\"red onions\"," + "\"garlic cloves\"," + "\"oregano\"," + "\"salt\"," + "\"lettuce leaves\"," + "\"tomatoes\"" + "]," +
                "\"RecipeInstructions\": [" + "\"Combine dressing and set aside. This includes: yogurt, dill, garlic powder, and cucumber.\"," + "\"In 12\\\\\"," + "\", \"," + "\", \"," + "\", \"" + "]," +
                "\"CookTime\": \"6 min\"," +
                "\"PrepTime\": \"30 min\"," +
                "\"TotalTime\": \"36 min\"" +
                "}]" +
                "]"));

        jsonResponse.getJSONObject("output").put("Repas_Programme", repasProgramme);

        Stock s = new Stock();
        s.setId(1L);
        s.setQuantiteCritiqueParDefaut(190);

        utilisateur.setStock(s);

        // Créer Recette
        Recette recette = new Recette();
        recette.setId(38L);
        recette.setIntitule("Low-Fat Berry Blue Frozen Dessert");
        recette.setDescription("Make and share this Low-Fat Berry Blue Frozen Dessert recipe from Food.com.");
        recette.setDureeTotal(1485);
        recette.setInstructionsDePreparation("[\"Toss 2 cups berries with sugar\", \"Let stand for 45 minutes\", \"stirring occasionally\", \"Transfer berry-sugar mixture to food processor\", \"Add yogurt and process until smooth\", \"Strain through fine sieve\", \"Pour into baking pan or transfer to ice cream maker and process according to manufacturer's directions\", \"Freeze uncovered until edges are solid but center is soft\", \"Transfer to processor and blend until smooth again\", \"Return to pan and freeze until edges are solid\", \"Transfer to processor and blend until smooth again\", \"Fold in remaining 2 cups of blueberries\", \"Pour into plastic mold and freeze overnight\", \"Let soften slightly to serve.\"]");
        recette.setImageUrl("https://img.sndimg.com/food/image/upload/w_555,h_416,c_fit,fl_progressive,q_95/v1/img/recipes/38/YUeirxMLQaeE1h3v3qnM_229%20berry%20blue%20frzn%20dess.jpg");

        // Créer Valeur Nutritionnel de recette
        ValeurNutritionnel valeurNutritionnel = new ValeurNutritionnel();
        valeurNutritionnel.setCarbohydrate(37.1);
        valeurNutritionnel.setEnegie(170.9);
        valeurNutritionnel.setFibre(3.6);
        valeurNutritionnel.setLipide(2.5);
        valeurNutritionnel.setProteine(3.2);
        valeurNutritionnel.setSucre(30.2);
        recette.setValeurNutritionnel(valeurNutritionnel);

        // Créer Catégorie de recette
        CategorieDeRecette categorieDeRecette = new CategorieDeRecette();
        categorieDeRecette.setIntitule("Frozen Desserts");
        recette.setCategorieDeRecette(categorieDeRecette);

        // Créer un ingredient de recette
        Ingredient ingredient = new Ingredient();
        ingredient.setId(2L);
        ingredient.setIntitule("blueberries");
        ingredient.setQuantity(4.0);
        ingredient.setRecette(recette);
        recette.setIngredients(new ArrayList<>(List.of(ingredient)));

        // Ajouter  la recette au stock et favoris d'utilisateur
        utilisateur.getStock().setRecette(new ArrayList<>(List.of(recette)));
        utilisateur.setRecettesFavoris(new ArrayList<>(List.of(recette)));

        List<Recette> recettesAuStock = utilisateur.getStock().getRecette();

        // Créer un produit et ajouter le dans stock d'utilisateur
        Produit produit = new Produit();
        produit.setId(1L);
        produit.setIntitule("blueberries");
        produit.setQuantite(10.0);

        utilisateur.getStock().setProduit(new ArrayList<>(List.of(produit)));
        List<Produit> produitsAuStock = utilisateur.getStock().getProduit();

        List<RecetteResponse> recettesResponse = recommendationService.processRecommendationResponse(jsonResponse,
                produitsAuStock,
                recettesAuStock,
                utilisateur);

        assertNotNull(recettesResponse);
    }

    @Test
    void testHasPreferredIngredients() {

        List<String> preferredIngredients1 = Arrays.asList("Ingredient1", "Ingredient2");
        List<String> preferredIngredients2 = Arrays.asList("Ingredient1");

        Recette recette = new Recette();
        recette.setIngredients(Arrays.asList(
                new Ingredient(1L, "Ingredient1", recette, 4.0, "", null),
                new Ingredient(1L, "Ingredient3", recette, 4.0, "", null)
        ));

        assertTrue(recommendationService.hasPreferredIngredients(recette, Arrays.asList()));

        assertFalse(recommendationService.hasPreferredIngredients(recette, preferredIngredients1));

        assertTrue(recommendationService.hasPreferredIngredients(recette, preferredIngredients2));
    }

    @Test
    void testHasRegimeSpeciaux() {
        Recette recette = new Recette();
        CategorieDeRecette categorie = new CategorieDeRecette();
        categorie.setIntitule("Categorie1");
        recette.setCategorieDeRecette(categorie);

        List<String> regimesSpeciaux = Arrays.asList("Categorie1", "Categorie2");

        assertTrue(recommendationService.hasRegimeSpeciaux(recette, regimesSpeciaux));

        // Test with empty regimesSpeciaux
        assertTrue(recommendationService.hasRegimeSpeciaux(recette, Arrays.asList()));

        // Test with non-matching categorieDeRecette
        assertFalse(recommendationService.hasRegimeSpeciaux(recette, Arrays.asList("Categorie3")));
    }
}
