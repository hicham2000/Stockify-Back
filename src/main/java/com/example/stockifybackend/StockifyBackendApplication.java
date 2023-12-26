package com.example.stockifybackend;

import com.example.stockifybackend.Entities.*;
import com.example.stockifybackend.Repositories.*;
import com.example.stockifybackend.services.CourseService;
import com.example.stockifybackend.services.UtilisateurService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.util.stream.Stream;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.example.stockifybackend")
public class StockifyBackendApplication implements CommandLineRunner {
    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(
                new ClassPathResource("stockify-9ed7f-firebase-adminsdk-3437r-aff5bf54fd.json").getInputStream());
        FirebaseOptions firebaseOptions = FirebaseOptions.builder().setCredentials(googleCredentials).build();
        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions,"stockify");
        return  FirebaseMessaging.getInstance(app);
    }

    @Autowired
    private ProduitRepository produitRepository ;

    @Autowired
    private ProduitAAcheterRepository produitAcheterRepository ;

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

    @Autowired
    private UtilisateurService utilisateurService;

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
        ListeCourse c=new ListeCourse();
       Stock s = new Stock();
        s.setQuantiteCritiqueParDefaut(190);
        stockRepository.save(s);

        Stock s2 = new Stock();
        s2.setQuantiteCritiqueParDefaut(10);

        Utilisateur user1 = new Utilisateur(1L,
                "wassim","rifay",
                "rifaywassim@gmail.com", "123456@Wassim",
                "", false);

        utilisateurService.addUtilisateur(user1);

        courseRepository.save(c);


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




      //  courseService.initProduit();
      //  courseService.initCourse();




    }
}
