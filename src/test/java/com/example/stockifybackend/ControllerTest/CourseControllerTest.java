package com.example.stockifybackend.ControllerTest;

import com.example.stockifybackend.Controllers.CourseController;
import com.example.stockifybackend.Entities.ProduitAAcheter;
import com.example.stockifybackend.Repositories.ListeCourseRepository;
import com.example.stockifybackend.services.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @Mock
    private ListeCourseRepository courseRepository;

    @InjectMocks
    private CourseController courseController;

    @Test
    public void testAddProductToListeCourse() {
        ResponseEntity<Void> responseEntity = courseController.addProductToListeCourse(1L, new ProduitAAcheter());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateProductInListeCourse() {
        ResponseEntity<String> responseEntity = courseController.updateProductInListeCourse(1L, 2L, new ProduitAAcheter());
        assertEquals("Produit a ete updater", responseEntity.getBody());
    }

    @Test
    public void testDeleteProductInListeCourse() {
        ResponseEntity<String> responseEntity = courseController.deleteProductInListeCourse(1L, 2L);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteProductAllListeCourse() {
        ResponseEntity<String> responseEntity = courseController.deleteProductAllListeCourse(1L);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testGetAllProduitListeCourse() {
        List<ProduitAAcheter> produits = new ArrayList<>();
        when(courseService.getAllProduitInCourse(anyLong())).thenReturn(produits);

        List<ProduitAAcheter> result = courseController.getAllProduitListeCourse(1L);

        assertEquals(produits, result);
    }

    @Test
    public void testGetProduiInCourse() {
        List<ProduitAAcheter> produits = new ArrayList<>();
        when(courseService.findByListeCourseProduit(anyLong(), anyString())).thenReturn(produits);

        List<ProduitAAcheter> result = courseController.getProduiInCourse(1L, "intitule");

        assertEquals(produits, result);
    }
}
