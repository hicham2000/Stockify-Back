package com.example.stockifybackend.ServiceTest;

import com.example.stockifybackend.Entities.Recette;
import com.example.stockifybackend.Entities.Utilisateur;
import com.example.stockifybackend.Repositories.RecetteRepository;
import com.example.stockifybackend.Repositories.UtilisateurRepository;
import com.example.stockifybackend.dto.RecetteResponse;
import com.example.stockifybackend.services.RecommendationService;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class RecommendationServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private RecetteRepository recetteRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RecommendationService recommendationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    void testGetRecommendedRecettes() throws JSONException {
//        // Mocking data
//        long userId = 1L;
//        LocalDateTime tempsDuClient = LocalDateTime.now();
//        String recommendationSystemUrl = "http://localhost:8081";
//        String jsonResponse = "{\"output\":{\"Repas_Programme\":{\"breakfast\":[{\"Recipe_Id\":1}]}}}";
//
//        // Mocking repository
//        when(utilisateurRepository.findById(userId)).thenReturn(Optional.of(new Utilisateur()));
//        when(recetteRepository.findById(anyLong())).thenReturn(Optional.of(new Recette()));
//
//        // Mocking RestTemplate
//        when(restTemplate.exchange(anyString(), any(), any(), eq(String.class)))
//                .thenReturn(ResponseEntity.ok(jsonResponse));
//
//        // Setting recommendationSystemUrl in the service
//        recommendationService.setRecommendationSystemUrl(recommendationSystemUrl);
//
//        // Testing the service method
//        List<RecetteResponse> recommendedRecettes = recommendationService.getRecommendedRecettes(userId);
//
//        // Assertions
//        assertEquals(1, recommendedRecettes.size());
//    }


}

