package com.example.stockifybackend;

import com.example.stockifybackend.Entities.*;
import com.example.stockifybackend.Repositories.*;
import com.example.stockifybackend.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StockifyBackendApplication implements CommandLineRunner {

    @Autowired
    private ProduitRepository produitRepository ;

    @Autowired
    private CategorieDeProduitsRepository categorieDeProduitsRepository;

    @Autowired
    private ValeurNutritionnelRepository valeurNutritionnelRepository;

    @Autowired
    private RecetteRepository recetteRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private ListeCourseRepository courseRepository;

    @Autowired
    private CourseService courseService;

    public static void main(String[] args) {

        SpringApplication.run(StockifyBackendApplication.class, args);
    }
    /*@Bean
    public CommandLineRunner demo(RoleRepository roleRepo) {
        return (args) -> {
            Role role=new Role();
            role.setName("ROLE_USER");
            roleRepo.save(role);
        };
    }*/
    @Override
    public void run(String... args) throws Exception {

       Stock s = new Stock();
        s.setQuantiteCritiqueParDefaut(190);
        stockRepository.save(s);



//        CategorieDeProduits categorieDeProduits = new CategorieDeProduits();
//        categorieDeProduits.setIntitule("hicham");
//
//        ValeurNutritionnel valeur = new ValeurNutritionnel(1L,3,4,6,7,3,4);
//        this.valeurNutritionnelRepository.save(valeur);
//
//        Stock stock = new Stock();
//        this.stockRepository.save(stock);
//
//
//
//
//
//        Recette recette = new Recette();
//        recette.setIntitule("fghj");
//        recette.setStock(stock);
//
//        this.recetteRepository.save(recette);
//
//        Ingredient ingredient = new Ingredient();
//        ingredient.setIntitule("kamoun");
//        ingredient.setRecette(recette);
//
//        ingredientRepository.save(ingredient);
//
//
//
//
//
//
//
//        Produit p = new Produit("testproduct1");
//        p.setIntitule("ghj");
//        p.setValeurNutritionnel(valeur);
//        p.setStock(stock);
//        this.produitRepository.save(p);
//        categorieDeProduits.setProduit(p);
//        this.categorieDeProduitsRepository.save(categorieDeProduits);



        courseService.initProduit();
      //  courseService.initCourse();



    }
}
