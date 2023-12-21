package com.example.stockifybackend.Controllers;

import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.Entities.ProduitAAcheter;
import com.example.stockifybackend.Repositories.ListeCourseRepository;
import com.example.stockifybackend.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listeCourses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ListeCourseRepository courseRepository;

    @PostMapping("/{courseId}/products")
    public ResponseEntity<Void> addProductToListeCourse(@PathVariable Long courseId,@RequestBody ProduitAAcheter produit){

      courseService.addProductToListeCourse(courseId,produit);
    return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{courseId}/products/{productId}")
    public ResponseEntity<String> updateProductInListeCourse(@PathVariable Long courseId,@PathVariable Long productId,@RequestBody ProduitAAcheter update){
        courseService.updateProductCourse(courseId,productId,update);
        return ResponseEntity.ok("Produit a ete updater");
    }

    @DeleteMapping("/{courseId}/products/{productId}")
    public ResponseEntity<String> deleteProductInListeCourse(@PathVariable Long courseId,@PathVariable Long productId){
        courseService.deleteProductToListeCourse(courseId,productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{courseId}/products")
    public List<ProduitAAcheter>getAllProduitListeCourse(@PathVariable Long courseId){
        return courseService.getAllProduitInCourse(courseId);
    }

    @GetMapping("/{courseId}/products/{intitule}")
    public List<ProduitAAcheter> getProduiInCourse(@PathVariable Long courseId,@PathVariable String intitule){
        return courseService.findByListeCourseProduit(courseId,intitule);
    }



}
