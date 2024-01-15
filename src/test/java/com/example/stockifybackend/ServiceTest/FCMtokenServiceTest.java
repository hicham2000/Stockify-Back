package com.example.stockifybackend.ServiceTest;

import com.example.stockifybackend.Entities.Utilisateur;
import com.example.stockifybackend.Repositories.UtilisateurRepository;
import com.example.stockifybackend.services.FCMtokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FCMtokenServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private FCMtokenService fcmTokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveFcmToken() {
        Long userId = 1L;
        String token = "testFcmToken";
        Utilisateur mockUser = new Utilisateur();
        mockUser.setId(userId);

        when(utilisateurRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        fcmTokenService.saveFcmToken(userId, token);

        verify(utilisateurRepository, times(1)).findById(userId);
        verify(utilisateurRepository, times(1)).save(mockUser);

        // Ensure the token is set correctly
        assertEquals(token, mockUser.getNotifToken());
    }

    @Test
    void testSaveFcmTokenWithNonExistingUser() {
        Long userId = 2L;
        String token = "testFcmToken";

        when(utilisateurRepository.findById(userId)).thenReturn(Optional.empty());

        fcmTokenService.saveFcmToken(userId, token);

        verify(utilisateurRepository, times(1)).findById(userId);
        verify(utilisateurRepository, never()).save(any());
        
    }
}