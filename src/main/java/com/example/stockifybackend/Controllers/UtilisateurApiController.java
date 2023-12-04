package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtilisateurApiController {
    @Autowired
    private UtilisateurService utilisateurService;
}
