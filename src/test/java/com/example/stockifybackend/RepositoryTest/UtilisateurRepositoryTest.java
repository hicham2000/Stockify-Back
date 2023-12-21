package com.example.stockifybackend.RepositoryTest;
import com.example.stockifybackend.Entities.Utilisateur;
import com.example.stockifybackend.Repositories.UtilisateurRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class UtilisateurRepositoryTest {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @BeforeEach
    void setUp() {
        // Initialiser ou pré-remplir la base de données avec des données de test si nécessaire
    }

    @AfterEach
    void tearDown() {
        // Nettoyer ou réinitialiser l'état de la base de données après chaque test si nécessaire
    }

    @Test
    void testFindByEmail() {
        // Enregistrer un utilisateur dans la base de données
        Utilisateur savedUser = utilisateurRepository.save(new Utilisateur(0L, "John", "Doe", "john.doe@example.com", "password", "", false));

        // Récupérer l'utilisateur par e-mail depuis le dépôt
        Optional<Utilisateur> retrievedUser = utilisateurRepository.findByEmail(savedUser.getEmail());

        // Assertions
        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getPrénom()).isEqualTo("John");
        assertThat(retrievedUser.get().getNom()).isEqualTo("Doe");
    }

    @Test
    void testExistsByEmail() {
        // Enregistrer un utilisateur dans la base de données
        utilisateurRepository.save(new Utilisateur(0L, "John", "Doe", "john.doe@example.com", "password", "", false));

        // Vérifier l'existence de l'utilisateur par e-mail
        boolean userExists = utilisateurRepository.existsByEmail("john.doe@example.com");

        // Assertions
        assertThat(userExists).isTrue();
    }
}