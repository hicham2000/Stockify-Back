package com.example.stockifybackend.ServiceTest;

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
import static org.junit.jupiter.api.Assertions.*;

class RecommendationServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private RecetteRepository recetteRepository;

    @Mock
    private StockService stockService;

    @InjectMocks
    private RecommendationService recommendationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCalculateAge() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date dateOfBirth = formatter.parse("09-12-2001");
        int age = recommendationService.calculateAge(dateOfBirth);
        assertEquals(22, age);
    }
}
