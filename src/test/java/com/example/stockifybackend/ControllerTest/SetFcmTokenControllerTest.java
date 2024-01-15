package com.example.stockifybackend.ControllerTest;

import com.example.stockifybackend.Controllers.SetFcmTokenController;
import com.example.stockifybackend.services.FCMtokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SetFcmTokenControllerTest {

    @Mock
    private FCMtokenService fcmTokenService;

    @InjectMocks
    private SetFcmTokenController setFcmTokenController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateFcmTokenWithValidToken() {
        Long userId = 1L;
        String validToken = "validFcmToken";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("token", validToken);

        doNothing().when(fcmTokenService).saveFcmToken(userId, validToken);

        ResponseEntity<String> responseEntity = setFcmTokenController.updateFcmToken(userId, requestBody);

        verify(fcmTokenService, times(1)).saveFcmToken(userId, validToken);
        assertEquals(ResponseEntity.ok("FCM token updated successfully"), responseEntity);
    }

    @Test
    void testUpdateFcmTokenWithInvalidToken() {
        Long userId = 2L;
        String invalidToken = ""; // Invalid token
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("token", invalidToken);

        ResponseEntity<String> responseEntity = setFcmTokenController.updateFcmToken(userId, requestBody);

        verify(fcmTokenService, never()).saveFcmToken(userId, invalidToken);
        assertEquals(ResponseEntity.badRequest().body("Invalid FCM token"), responseEntity);
    }
}