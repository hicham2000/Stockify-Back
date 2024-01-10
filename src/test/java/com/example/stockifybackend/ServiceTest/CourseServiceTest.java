package com.example.stockifybackend.ServiceTest;

import com.example.stockifybackend.Entities.ListeCourse;
import com.example.stockifybackend.Entities.ProduitAAcheter;
import com.example.stockifybackend.Repositories.ListeCourseRepository;
import com.example.stockifybackend.Repositories.ProduitAAcheterRepository;
import com.example.stockifybackend.services.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CourseServiceTest {

    @Mock
    private ListeCourseRepository listeCourseRepository;

    @Mock
    private ProduitAAcheterRepository produitAAcheterRepository;


    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProductToListeCourse() {
        // Arrange
        Long courseId = 1L;
        ListeCourse listeCourse = new ListeCourse();
        when(listeCourseRepository.findById(courseId)).thenReturn(Optional.of(listeCourse));
        ProduitAAcheter produit = new ProduitAAcheter();

        // Act
        courseService.addProductToListeCourse(courseId, produit);

        // Assert
        assertEquals(listeCourse, produit.getListeCourse());
        verify(produitAAcheterRepository, times(1)).save(produit);
        verify(listeCourseRepository, times(1)).save(listeCourse);
    }

    @Test
    public void testDeleteProductToListeCourse() {
        // Arrange
        Long courseId = 1L;
        Long produitId = 2L;
        ListeCourse listeCourse = new ListeCourse();
        ProduitAAcheter produit = new ProduitAAcheter();
        produit.setId(produitId);
        listeCourse.getProduit().add(produit);
        when(listeCourseRepository.findById(courseId)).thenReturn(Optional.of(listeCourse));

        // Act
        courseService.deleteProductToListeCourse(courseId, produitId);

        // Assert
        assertEquals(0, listeCourse.getProduit().size());
        verify(produitAAcheterRepository, times(1)).deleteById(produitId);
        verify(listeCourseRepository, times(1)).save(listeCourse);
    }

    @Test
    public void testUpdateProductCourse() {
        // Arrange
        Long courseId = 1L;
        Long productId = 2L;
        ListeCourse listeCourse = new ListeCourse();
        ProduitAAcheter produit = new ProduitAAcheter();
        produit.setId(productId);
        listeCourse.getProduit().add(produit);
        when(listeCourseRepository.findById(courseId)).thenReturn(Optional.of(listeCourse));
        ProduitAAcheter productUpdate = new ProduitAAcheter();
        productUpdate.setIntitule("Updated Product");

        // Act
        courseService.updateProductCourse(courseId, productId, productUpdate);

        // Assert
        assertEquals("Updated Product", produit.getIntitule());
        verify(produitAAcheterRepository, times(1)).save(produit);
        verify(listeCourseRepository, times(1)).save(listeCourse);
    }

    @Test
    public void testGetAllProduitInCourse() {
        // Arrange
        Long courseId = 1L;
        List<ProduitAAcheter> produits = new ArrayList<>();
        when(produitAAcheterRepository.findAllByListeCourseIdCustomQuery(courseId)).thenReturn(produits);

        // Act
        List<ProduitAAcheter> result = courseService.getAllProduitInCourse(courseId);

        // Assert
        assertEquals(produits, result);
    }

    @Test
    public void testInitProduit() {

        // Arrange
        ListeCourse c = new ListeCourse();
        when(listeCourseRepository.save(c)).thenReturn(c);
        when(produitAAcheterRepository.save(any(ProduitAAcheter.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        // Act
        courseService.initProduit();

        // Assert
        assertEquals(6, c.getProduit().size());
        verify(listeCourseRepository, times(6)).save(c);
        verify(produitAAcheterRepository, times(6)).save(any(ProduitAAcheter.class));
    }

}
