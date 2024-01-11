package com.example.stockifybackend.services;
import com.example.stockifybackend.Entities.Utilisateur;
import com.example.stockifybackend.Repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
public class FCMtokenService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    public void saveFcmToken(Long id, String token) {

        Optional<Utilisateur> optionalUser = utilisateurRepository.findById(id);
        optionalUser.ifPresent(user -> {
            user.setNotifToken(token);
            utilisateurRepository.save(user);
        });
    }

}
