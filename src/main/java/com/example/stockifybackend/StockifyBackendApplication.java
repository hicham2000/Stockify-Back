package com.example.stockifybackend;

import com.example.stockifybackend.Entities.*;
import com.example.stockifybackend.Repositories.*;
import com.example.stockifybackend.services.CourseService;
import com.example.stockifybackend.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class StockifyBackendApplication implements CommandLineRunner {

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
        ListeCourse c = new ListeCourse();
       Stock s = new Stock();
        s.setQuantiteCritiqueParDefaut(190);
        s = stockRepository.save(s);

        Stock s2 = new Stock();
        s2.setQuantiteCritiqueParDefaut(10);

        Utilisateur user1 = new Utilisateur(1L,
                "wassim","rifay",
                "rifaywassim@gmail.com", "123456@Wassim",
                "", false);


        c = courseRepository.save(c);

        user1.setStock_id(s.getId());
        user1.setListeDeCourse_id(c.getId());

        utilisateurService.addUtilisateur(user1);


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
