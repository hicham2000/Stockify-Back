package com.example.stockifybackend.Controllers;
import com.example.stockifybackend.services.FCMtokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class SetFcmTokenController {
    @Autowired
    private FCMtokenService fcmTokenService;

    @PutMapping("/{id}/updateFcmToken")
    public ResponseEntity<String> updateFcmToken(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        String token = requestBody.get("token");


        if (token != null && !token.isEmpty()) {
            fcmTokenService.saveFcmToken(id, token);
            return ResponseEntity.ok("FCM token updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid FCM token");
        }
    }

}
