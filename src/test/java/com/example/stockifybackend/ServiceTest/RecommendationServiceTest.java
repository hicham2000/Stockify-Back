package com.example.stockifybackend.ServiceTest;

import com.example.stockifybackend.Repositories.RecetteRepository;
import com.example.stockifybackend.Repositories.UtilisateurRepository;
import com.example.stockifybackend.services.RecommendationService;
import com.example.stockifybackend.services.StockService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
}
