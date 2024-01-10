package com.example.stockifybackend.services;

import com.example.stockifybackend.Entities.ListeCourse;
import com.example.stockifybackend.Entities.Produit;
import com.example.stockifybackend.Entities.ProduitAAcheter;
import com.example.stockifybackend.Entities.Stock;
import com.example.stockifybackend.Repositories.ListeCourseRepository;
import com.example.stockifybackend.Repositories.ProduitAAcheterRepository;
import com.example.stockifybackend.Repositories.ProduitRepository;
import com.example.stockifybackend.Repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CourseService {

    @Autowired
    private ListeCourseRepository listeCourseRepository;

    @Autowired
    private ProduitAAcheterRepository produitRepository;

    @Autowired
    private StockRepository stockRepository;

   public void addProductToListeCourse(Long courseId, ProduitAAcheter produit){
       Optional<ListeCourse> optionalListeCourse=listeCourseRepository.findById(courseId);

       if(optionalListeCourse.isPresent()){
           ListeCourse listeCourse=optionalListeCourse.get();
           produit.setListeCourse(listeCourse);
           produitRepository.save(produit);
           listeCourse.getProduit().add(produit);
           listeCourseRepository.save(listeCourse);
       }
    }

    public void deleteProductToListeCourse(Long courseId,Long ProduitId ){

        Optional<ListeCourse> listeCourseOption=listeCourseRepository.findById(courseId);

        if(listeCourseOption.isPresent()){
            ListeCourse couse=listeCourseOption.get();
            List<ProduitAAcheter> produits=couse.getProduit();
            produits.removeIf(produit -> produit.getId().equals(ProduitId));
            produitRepository.deleteById(ProduitId);
            listeCourseRepository.save(couse);
        }
    }

    public void updateProductCourse(Long courseId, Long productId, ProduitAAcheter productUpdate){
       Optional<ListeCourse> listeCourseOptional=listeCourseRepository.findById(courseId);

       if(listeCourseOptional.isPresent()){
           ListeCourse course=listeCourseOptional.get();
           List<ProduitAAcheter> produits=course.getProduit();

           Iterator<ProduitAAcheter> iterator = produits.iterator();
           while (iterator.hasNext()) {
               ProduitAAcheter produit = iterator.next();
               if (produit.getId().equals(productId)) {
                   produit.setIntitule(productUpdate.getIntitule());
                   produit.setQuantite(productUpdate.getQuantite());
                   produit.setUniteDeMesure(productUpdate.getUniteDeMesure());
                   produit.setEtat(productUpdate.isEtat());

                   produitRepository.save(produit);
                   listeCourseRepository.save(course);
               }
           }

       }
    }

    public List<ProduitAAcheter> getAllProduitInCourse(Long courseId){
                  return produitRepository.findAllByListeCourseIdCustomQuery(courseId);
    }
     public List<ProduitAAcheter> findByListeCourseProduit(Long courseId,String intitule){
       return produitRepository.findByListeCourseProduit(courseId,intitule);
     }

    public void supprimerProduitaacheterParIdProduit(Long courseId) {
        Optional<ListeCourse> listeCourseOption=listeCourseRepository.findById(courseId);

        if(listeCourseOption.isPresent()){
            ListeCourse couse=listeCourseOption.get();
            List<ProduitAAcheter> produits = new ArrayList<>(couse.getProduit());
            for (ProduitAAcheter produit : produits) {


                    produitRepository.deleteById(produit.getId());

            }
            produits.clear();
            listeCourseRepository.save(couse);
        }
    }



    public void initProduit(){
     //   Stock s = new Stock();
     //   s.setQuantiteCritiqueParDefaut(10);
        ListeCourse c=new ListeCourse();
        Stream.of("Lait","Pattes","Lazagnes","Spagitti","Tomates","Fromage").forEach(nameProduct->{
            ProduitAAcheter produit=new ProduitAAcheter();
            produit.setIntitule(nameProduct);


         //   stockRepository.save(s);
          //  produit.setStock(s);
            listeCourseRepository.save(c);
            produit.setListeCourse(c);

            produitRepository.save(produit);


        });
    }



   // public void initCourse(){
     //  ListeCourse course=new ListeCourse();
     //  List<Produit> produits=produitRepository.findAll();
     //  course.setProduit(produits);
     //  listeCourseRepository.save(course);
     //   ListeCourse course1=new ListeCourse();
     //   listeCourseRepository.save(course1);
   // }




}
