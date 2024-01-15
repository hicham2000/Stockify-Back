package com.example.stockifybackend.EntitiesTest;

import com.example.stockifybackend.Entities.Recette;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecetteTest {

    @Test
    void testSetInstructionsDePreparation() throws IOException {
        // Arrange
        Recette recette = new Recette();
        List<String> instructionsList = Arrays.asList("Step 1", "Step 2", "Step 3");

        // Act
        recette.setInstructionsList(instructionsList);

        // Assert
        assertEquals(instructionsList, recette.getInstructionsList());
        String expectedJson = new ObjectMapper().writeValueAsString(instructionsList);
        assertEquals(expectedJson, recette.getInstructionsDePreparation());
    }

    @Test
    void testSetInstructionsList() throws IOException {
        // Arrange
        Recette recette = new Recette();
        String instructionsJson = "[\"Step 1\", \"Step 2\", \"Step 3\"]";

        // Act
        recette.setInstructionsDePreparation(instructionsJson);

        // Assert
        assertEquals(instructionsJson, recette.getInstructionsDePreparation());
        List<String> expectedList = new ObjectMapper().readValue(instructionsJson, List.class);
        assertEquals(expectedList, recette.getInstructionsList());
    }
}
