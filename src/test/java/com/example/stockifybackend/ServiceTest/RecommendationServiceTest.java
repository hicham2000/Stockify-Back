package com.example.stockifybackend.ServiceTest;

import com.example.stockifybackend.Entities.Utilisateur;
import com.example.stockifybackend.Repositories.RecetteRepository;
import com.example.stockifybackend.Repositories.UtilisateurRepository;
import com.example.stockifybackend.services.RecommendationService;
import com.example.stockifybackend.services.StockService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
    void sendRecommendationRequest_Success() throws JSONException, ParseException {
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
    void sendRecommendationRequest_Failure_404() throws ParseException, JSONException {
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
    void sendRecommendationRequest_Failure_307() throws ParseException, JSONException {
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
    void sendRecommendationRequest_Failure_Host_Invalid() throws ParseException, JSONException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date dateDeNaissance = formatter.parse("09-12-2001");

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setDateDeNaissance(dateDeNaissance);
        utilisateur.setTaille("179");
        utilisateur.setPoids("62");
        utilisateur.setSexe("Homme");
        utilisateur.setModeSportif(false);

        String requestJson = recommendationService.buildRecommendationRequestJson(utilisateur);

        String url = RECOMMENDATION_SYSTEM_URL.replace("8081", "8080") + "/Repas_suggestions";
        JSONObject jsonResponse = recommendationService.sendRecommendationRequest(requestJson, url);

        assertNull(jsonResponse);
    }

}
