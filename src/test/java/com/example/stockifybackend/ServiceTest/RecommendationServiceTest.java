package com.example.stockifybackend.ServiceTest;

import com.example.stockifybackend.Entities.Utilisateur;
import com.example.stockifybackend.Repositories.RecetteRepository;
import com.example.stockifybackend.Repositories.UtilisateurRepository;
import com.example.stockifybackend.services.RecommendationService;
import com.example.stockifybackend.services.StockService;
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
import static org.mockito.Mockito.when;

class RecommendationServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private RecetteRepository recetteRepository;

    @Mock
    private StockService stockService;
    @Mock
    private Utilisateur utilisateur;

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
        assertTrue(requestJson.contains("\"age\":"));
        assertTrue(requestJson.contains("\"height\":"));
        assertTrue(requestJson.contains("\"weight\":"));
        assertTrue(requestJson.contains("\"gender\":"));
        assertTrue(requestJson.contains("\"activity\":"));
        assertTrue(requestJson.contains("\"number_of_meals\":"));
        assertTrue(requestJson.contains("\"weight_loss_plan\":"));
    }
}
