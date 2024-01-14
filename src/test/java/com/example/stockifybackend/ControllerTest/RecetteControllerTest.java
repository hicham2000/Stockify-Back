package com.example.stockifybackend.ControllerTest;

import com.example.stockifybackend.Entities.Recette;
import com.example.stockifybackend.Repositories.RecetteRepository;
import com.example.stockifybackend.Controllers.RecetteController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RecetteControllerTest {

    @Mock
    private RecetteRepository recetteRepository;

    @InjectMocks
    private RecetteController recetteController;

    @Test
    void testGetRecetteByID_RecetteExists() {
        // Arrange
        Long recetteId = 38L;
        Recette mockRecette = new Recette();
        mockRecette.setId(recetteId);
        when(recetteRepository.findById(recetteId)).thenReturn(Optional.of(mockRecette));

        // Act
        ResponseEntity<?> responseEntity = recetteController.getRecetteByID(recetteId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Map);

        Map<?, ?> responseBody = (Map<?, ?>) responseEntity.getBody();
        assertEquals("Recette récupéré par succès", responseBody.get("message"));
        assertTrue(responseBody.containsKey("recette"));
        assertEquals(mockRecette, responseBody.get("recette"));
    }
}
