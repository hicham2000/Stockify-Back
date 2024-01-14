package com.example.stockifybackend.RepositoryTest;

import com.example.stockifybackend.Entities.ListeCourse;
import com.example.stockifybackend.Repositories.ListeCourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class CourseRepositoryTest {

    @Autowired
    private ListeCourseRepository listeCourseRepository;

    @Test
    public void testSaveAndRetrieveListeCourse() {
        // Création d'une instance de ListeCourse
        ListeCourse listeCourse = new ListeCourse();

        // Sauvegarde de la ListeCourse dans la base de données
        listeCourseRepository.save(listeCourse);

        // Récupération de toutes les ListesCourse depuis la base de données
        List<ListeCourse> retrievedListesCourse = listeCourseRepository.findAll();

        // Vérification que la ListeCourse sauvegardée est présente dans la liste récupérée
        assertTrue(retrievedListesCourse.contains(listeCourse));
    }

    @Test
    public void testDeleteListeCourse() {
        // Création d'une instance de ListeCourse
        ListeCourse listeCourse = new ListeCourse();

        // Sauvegarde de la ListeCourse dans la base de données
        listeCourseRepository.save(listeCourse);

        // Suppression de la ListeCourse de la base de données
        listeCourseRepository.delete(listeCourse);

        // Récupération de toutes les ListesCourse depuis la base de données
        List<ListeCourse> retrievedListesCourse = listeCourseRepository.findAll();

        // Vérification que la ListeCourse a été supprimée et n'est plus présente dans la liste récupérée
        assertEquals(0, retrievedListesCourse.size());
    }
}
