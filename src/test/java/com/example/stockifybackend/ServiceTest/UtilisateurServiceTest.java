package com.example.stockifybackend.ServiceTest;

import com.example.stockifybackend.Entities.Utilisateur;
import com.example.stockifybackend.Repositories.UtilisateurRepository;
import com.example.stockifybackend.services.UtilisateurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UtilisateurServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private UtilisateurService utilisateurService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddUtilisateur() {
        Utilisateur utilisateur = new Utilisateur();
        when(utilisateurRepository.save(utilisateur)).thenReturn(utilisateur);

        Utilisateur savedUtilisateur = utilisateurService.addUtilisateur(utilisateur);

        assertEquals(utilisateur, savedUtilisateur);
        verify(utilisateurRepository, times(1)).save(utilisateur);
    }

    @Test
    void testDeleteUtilisateur() {
        Long userId = 1L;
        when(utilisateurRepository.findById(userId)).thenReturn(Optional.of(new Utilisateur()));

        utilisateurService.deleteUtilisateur(userId);

        verify(utilisateurRepository, times(1)).delete(any());
    }

    @Test
    void testDeleteUtilisateurNotFound() {
        Long userId = 1L;
        when(utilisateurRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> utilisateurService.deleteUtilisateur(userId));
        verify(utilisateurRepository, never()).delete(any());
    }

    @Test
    void testGetUtilisateur() {
        Long userId = 1L;
        Utilisateur utilisateur = new Utilisateur();
        when(utilisateurRepository.findById(userId)).thenReturn(Optional.of(utilisateur));

        Utilisateur retrievedUtilisateur = utilisateurService.getUtilisateur(userId);

        assertEquals(utilisateur, retrievedUtilisateur);
    }

    @Test
    void testGetUtilisateurNotFound() {
        Long userId = 1L;
        when(utilisateurRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> utilisateurService.getUtilisateur(userId));
    }

    @Test
    void testGetUtilisateurByEmail() {
        String email = "test@example.com";
        Utilisateur utilisateur = new Utilisateur();
        when(utilisateurRepository.findByEmail(email)).thenReturn(Optional.of(utilisateur));

        Utilisateur retrievedUtilisateur = utilisateurService.getUtilisateurByEmail(email);

        assertEquals(utilisateur, retrievedUtilisateur);
    }

    @Test
    void testGetUtilisateurByEmailNotFound() {
        String email = "test@example.com";
        when(utilisateurRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> utilisateurService.getUtilisateurByEmail(email));
    }

    @Test
    void testIsUserExists() {
        String email = "test@example.com";
        when(utilisateurRepository.existsByEmail(email)).thenReturn(true);

        boolean result = utilisateurService.isUserExists(email);

        assertEquals(true, result);
    }
}
