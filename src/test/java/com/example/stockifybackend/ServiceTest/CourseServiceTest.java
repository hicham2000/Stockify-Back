package com.example.stockifybackend.ServiceTest;

import com.example.stockifybackend.Entities.ListeCourse;
import com.example.stockifybackend.Entities.ProduitAAcheter;
import com.example.stockifybackend.Repositories.ListeCourseRepository;
import com.example.stockifybackend.Repositories.ProduitAAcheterRepository;
import com.example.stockifybackend.services.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CourseServiceTest {

    @Mock
    private ListeCourseRepository listeCourseRepository;

    @Mock
    private ProduitAAcheterRepository produitAAcheterRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    public void testAddProductToListeCourse() {
        ListeCourse listeCourse = new ListeCourse();
        ProduitAAcheter produitAAcheter = new ProduitAAcheter();

        when(listeCourseRepository.findById(anyLong())).thenReturn(Optional.of(listeCourse));
        when(produitAAcheterRepository.save(any(ProduitAAcheter.class))).thenReturn(produitAAcheter);

        courseService.addProductToListeCourse(1L, produitAAcheter);

        assertEquals(listeCourse, produitAAcheter.getListeCourse());
        verify(produitAAcheterRepository, times(1)).save(any(ProduitAAcheter.class));
        verify(listeCourseRepository, times(1)).save(any(ListeCourse.class));
    }

    @Test
    public void testDeleteProductToListeCourse() {
        ListeCourse listeCourse = new ListeCourse();
        ProduitAAcheter produitAAcheter = new ProduitAAcheter();
        produitAAcheter.setId(1L);

        when(listeCourseRepository.findById(anyLong())).thenReturn(Optional.of(listeCourse));
        when(produitAAcheterRepository.findById(anyLong())).thenReturn(Optional.of(produitAAcheter));

        courseService.deleteProductToListeCourse(1L, 1L);

        verify(produitAAcheterRepository, times(1)).deleteById(anyLong());
        verify(listeCourseRepository, times(1)).save(any(ListeCourse.class));
    }

    @Test
    public void testUpdateProductCourse() {
        ListeCourse listeCourse = new ListeCourse();
        ProduitAAcheter produitAAcheter = new ProduitAAcheter();
        produitAAcheter.setId(1L);

        when(listeCourseRepository.findById(anyLong())).thenReturn(Optional.of(listeCourse));
        when(produitAAcheterRepository.save(any(ProduitAAcheter.class))).thenReturn(produitAAcheter);

        courseService.updateProductCourse(1L, 1L, new ProduitAAcheter());

        verify(produitAAcheterRepository, times(1)).save(any(ProduitAAcheter.class));
        verify(listeCourseRepository, times(1)).save(any(ListeCourse.class));
    }

    @Test
    public void testGetAllProduitInCourse() {
        ListeCourse listeCourse = new ListeCourse();
        List<ProduitAAcheter> produits = new ArrayList<>();
        listeCourse.setProduit(produits);

        when(listeCourseRepository.findById(anyLong())).thenReturn(Optional.of(listeCourse));

        List<ProduitAAcheter> result = courseService.getAllProduitInCourse(1L);

        assertEquals(produits, result);
    }

    @Test
    public void testFindByListeCourseProduit() {
        when(produitAAcheterRepository.findByListeCourseProduit(anyLong(), anyString())).thenReturn(new ArrayList<>());

        List<ProduitAAcheter> result = courseService.findByListeCourseProduit(1L, "intitule");

        assertEquals(0, result.size());
    }

    @Test
    public void testSupprimerProduitaacheterParIdProduit() {
        ListeCourse listeCourse = new ListeCourse();
        ProduitAAcheter produitAAcheter = new ProduitAAcheter();
        produitAAcheter.setId(1L);
        listeCourse.getProduit().add(produitAAcheter);

        when(listeCourseRepository.findById(anyLong())).thenReturn(Optional.of(listeCourse));

        courseService.supprimerProduitaacheterParIdProduit(1L);

        verify(produitAAcheterRepository, times(1)).deleteById(anyLong());
        verify(listeCourseRepository, times(1)).save(any(ListeCourse.class));
    }


}
