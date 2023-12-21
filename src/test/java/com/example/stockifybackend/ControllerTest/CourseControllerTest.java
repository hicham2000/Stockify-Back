package com.example.stockifybackend.ControllerTest;

import com.example.stockifybackend.Controllers.CourseController;
import com.example.stockifybackend.Entities.ProduitAAcheter;
import com.example.stockifybackend.services.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @Test
    void testAddProductToListeCourse() {
        // Arrange
        Long courseId = 1L;
        ProduitAAcheter mockProduit = new ProduitAAcheter();
        doNothing().when(courseService).addProductToListeCourse(courseId, mockProduit);

        // Act
        ResponseEntity<Void> result = courseController.addProductToListeCourse(courseId, mockProduit);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void testUpdateProductInListeCourse() {
        // Arrange
        Long courseId = 1L;
        Long productId = 2L;
        ProduitAAcheter mockUpdate = new ProduitAAcheter();
        doNothing().when(courseService).updateProductCourse(courseId, productId, mockUpdate);

        // Act
        ResponseEntity<String> result = courseController.updateProductInListeCourse(courseId, productId, mockUpdate);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Produit a ete updater", result.getBody());
    }

    @Test
    void testDeleteProductInListeCourse() {
        // Arrange
        Long courseId = 1L;
        Long productId = 2L;
        doNothing().when(courseService).deleteProductToListeCourse(courseId, productId);

        // Act
        ResponseEntity<String> result = courseController.deleteProductInListeCourse(courseId, productId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    // Add similar tests for other controller methods: getAllProduitListeCourse, getProduiInCourse
    // ...

}

