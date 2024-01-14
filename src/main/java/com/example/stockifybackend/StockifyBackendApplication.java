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


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.io.IOException;
import java.util.Locale;
import java.util.stream.Stream;

@SpringBootApplication
@EnableScheduling
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
    private UtilisateurRepository utilisateurRepository ;

    @Autowired
    private ProduitRepository produitRepository ;

    @Autowired
    private ProduitGlobaleRepository produitGlobaleRepository ;

    @Autowired
    private ProduitAAcheterRepository produitAcheterRepository ;

    @Autowired
    private CategorieDeProduitsRepository categorieDeProduitsRepository;

    @Autowired
    private ValeurNutritionnelRepository valeurNutritionnelRepository;

    @Autowired
    private RecetteRepository recetteRepository;

    @Autowired
    private RepasRepository repasRepository;

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

        Utilisateur user1 = new Utilisateur();
        user1.setId(1L);
        user1.setPrénom("wassim");
        user1.setNom("rifay");
        user1.setEmail("rifaywassim@gmail.com");
        user1.setPassword("123456@Wassim");
        user1.setRégimeSpécieux("");
        user1.setModeSportif(false);

        user1.setSexe("Homme");
        user1.setTaille("179");
        user1.setPoids("62");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date dateDeNaissance = formatter.parse("09-12-2001");
        user1.setDateDeNaissance(dateDeNaissance);

        ListeCourse c = new ListeCourse();
        c.setId(2L);
        c = courseRepository.save(c);

        Stock s = new Stock();
        s.setId(1L);
        s.setQuantiteCritiqueParDefaut(190);
        s = stockRepository.save(s);

//        Stock s2 = new Stock();
//        s2.setId(2L);
//        s2.setQuantiteCritiqueParDefaut(10);
//        s2 = stockRepository.save(s2);


        user1.setStock(s);
        System.out.println("-------------------------------------------------------------------");
        s.setUtilisateur(user1);
        user1.setListeDeCourse_id(c.getId());

        user1 = utilisateurRepository.saveAndFlush(user1);


        courseRepository.save(c);


        String[] products = {
                "Nutella",
                "Kellogg's Corn Flakes",
                "Coca-Cola",
                "Oreo Cookies",
                "Heinz Ketchup",
                "Nestlé KitKat",
                "Pringles",
                "Bounty Chocolate Bar",
                "Cadbury Dairy Milk",
                "McCormick Spices",
                "Lay's Potato Chips",
                "Pepsi",
                "Campbell's Tomato Soup",
                "Hershey's Chocolate",
                "Quaker Oats",
                "Ferrero Rocher",
                "M&M's",
                "Doritos",
                "Jif Peanut Butter",
                "Snickers Chocolate Bar"
        };

        for(int i=0 ; i < 20 ; i++ ){
            Produit p = new Produit();
            p.setIntitule(products[i]);
            p.setStock(s);
            p.setQuantite(10);
            p.setUniteDeMesure("KG");
            produitRepository.save(p);

        }

        for(int i=0 ; i < 20 ; i++ ){
            ProduitGlobale p = new ProduitGlobale();
            p.setIntitule(products[i]);
            p.setUniteDeMesure("KG");
            p.setImageUrl("default");
            produitGlobaleRepository.save(p);
        }

        String [] categories = {"Refrigerateur","Congelateur","garde-manger"};

        for (int i = 0; i<categories.length ; i++){
            CategorieDeProduits ct = new CategorieDeProduits();
            ct.setIntitule(categories[i]);
            categorieDeProduitsRepository.save(ct);
        }



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

        //Ajouter Produit avec is_deleted==1 pour tester la corbeille

        Produit produit=new Produit();
        produit.setIs_deleted(1);
        produit.setQuantite(10);
        produit.setUniteDeMesure("KG");
        produit.setIntitule("test");
        produit.setStock(s);
        produitRepository.save(produit);
        //Ajouter Repas avec is_deleted==1 pour tester la corbeille
        Repas repas = new Repas();
        repas.setIntitule("RepasTest");
        repas.setStock(s);
        repas.setIs_deleted(1);
        this.repasRepository.save(repas);

    }
}
