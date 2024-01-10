package com.example.stockifybackend.ControllerTest;

import com.example.stockifybackend.Controllers.UtilisateurApiController;
import com.example.stockifybackend.Entities.LogingUtilisateur;
import com.example.stockifybackend.Entities.Utilisateur;
import com.example.stockifybackend.Repositories.UtilisateurRepository;
import com.example.stockifybackend.services.UtilisateurService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UtilisateurApiController.class)
public class UtilisateurApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UtilisateurService utilisateurService;

    @MockBean
    private UtilisateurRepository utilisateurRepository;

    @Test
    public void testAddUtilisateur() throws Exception {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setEmail("test@example.com");
        utilisateur.setPassword("password");

        Mockito.when(utilisateurService.isUserExists(Mockito.anyString())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(utilisateur)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("You are registred Successfully!"));

        Mockito.verify(utilisateurService, Mockito.times(1)).addUtilisateur(Mockito.any(Utilisateur.class));
    }

    @Test
    public void testAuthenticateUser() throws Exception {
        LogingUtilisateur logingUtilisateur = new LogingUtilisateur("test@example.com", "password");

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setEmail("test@example.com");
        utilisateur.setPassword("password");

        Mockito.when(utilisateurService.getUtilisateurByEmail(Mockito.anyString())).thenReturn(utilisateur);
        Mockito.when(utilisateurService.isUserExists(Mockito.anyString())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/Login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(logingUtilisateur)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Login successfully :)"));
    }

    @Test
    public void testLogoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/logout"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User logged out successfully!..."));
    }

    @Test
    public void testDeleteUtilisateur() throws Exception {
        Long userId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/Utilisateurs/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(utilisateurService, Mockito.times(1)).deleteUtilisateur(userId);
    }

    @Test
    public void testGetUtilisateur() throws Exception {
        Long userId = 1L;
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(userId);
        utilisateur.setEmail("test@example.com");
        utilisateur.setPassword("password");

        Mockito.when(utilisateurService.getUtilisateur(userId)).thenReturn(utilisateur);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/Utilisateur/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("password"));
    }

    // Utility method to convert objects to JSON string
    private String asJsonString(Object obj) throws Exception {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
