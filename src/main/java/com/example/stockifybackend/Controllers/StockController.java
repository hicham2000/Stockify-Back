package com.example.stockifybackend.Controllers;


import com.example.stockifybackend.Entities.*;
import com.example.stockifybackend.Repositories.*;
import com.example.stockifybackend.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private RepasRepository repasRepository;

    @Autowired
    private CategorieDeProduitsRepository categorieDeProduitsRepository ;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private BudgetRepository budgetRepository;


    @PostMapping("/repas")
    public ResponseEntity<String> addRepasToStock(@RequestBody RequestBodyData requestBodyData) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

        Repas repas = new Repas();
        repas.setIntitule(requestBodyData.getIntitule());
        repas.setDatePeremtion(dateFormat.parse(requestBodyData.getDatePeremtion()));
        repas.setDateAlert(dateFormat.parse(requestBodyData.getDateAlert()));

        Stock s = stockRepository.findById(Long.parseLong(requestBodyData.getStock())).get();
        repas.setStock(s);

        repas.setCategories(requestBodyData.getSpinnerText());
        List<Ingredient> ing = new ArrayList<>();




        repasRepository.save(repas);

        for(int i= 0 ; i<requestBodyData.getArraylist_of_product().size(); i++){
            Produit p = produitRepository.findById(requestBodyData.getArraylist_of_product().get(i).getId()).get();
            p.setQuantite(p.getQuantite()-requestBodyData.getArraylist_of_product().get(i).getQuantite());
            if(p.getQuantite() == 0){
                p.setIs_deleted(1);

            }
            produitRepository.save(p);
            Ingredient in = new Ingredient();
            in.setIntitule(requestBodyData.getArraylist_of_product().get(i).getIntitule());
            in.setQuantity(requestBodyData.getArraylist_of_product().get(i).getQuantite());
            in.setUniteDeMesure(p.getUniteDeMesure());
            in.setRepas(repas);
            ing.add(in);
            ingredientRepository.save(in);

        }


        return ResponseEntity.ok("added");
    }

    @PutMapping("/repas")
    public ResponseEntity<String> UpdateRepasInStock(@RequestBody RequestBodyDataRepas RequestBodyDataRepas) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date datep = inputFormat.parse(RequestBodyDataRepas.getDatePeremtion());



        Repas repas = repasRepository.findById(Long.valueOf(RequestBodyDataRepas.getId())).get();
        repas.setIntitule(RequestBodyDataRepas.getIntitule());
        repas.setDatePeremtion(datep);
        repas.setDateAlert(inputFormat.parse(RequestBodyDataRepas.getDateAlert()));
        repas.setCategories(RequestBodyDataRepas.getSpinnerText());

        repasRepository.save(repas);

        return ResponseEntity.ok("added");
    }

    @DeleteMapping("/repas/{id}")
    public ResponseEntity<String> DeleteRepasInStock(@PathVariable Long id) throws ParseException {


        Repas repas = repasRepository.findById(id).get();
        repas.setIs_deleted(1);


        repasRepository.save(repas);

        return ResponseEntity.ok("deleted");
    }

    @GetMapping("/repasbyid/{id}")
    public ResponseEntity<Repas> getRepasbyId(@PathVariable Long id) throws ParseException {
        Repas repas = repasRepository.findById(id).orElse(null);

        if (repas == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(repas);
    }

    @GetMapping("/productid/{id}")
    public ResponseEntity<Produit> getProduitbyId(@PathVariable Long id) throws ParseException {
        Produit produit = produitRepository.findById(id).orElse(null);

        if (produit == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(produit);
    }

    @PostMapping("/{stockId}/recipes")
    public ResponseEntity<Void> addRecipeToStock(@PathVariable Long stockId, @RequestBody Recette recette) {
        stockService.addRecipeToStock(stockId, recette);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{stockId}/recipes/{recetteId}")
    public ResponseEntity<?> addRecipeToStockByRecetteID(@PathVariable Long stockId, @PathVariable Long recetteId) {
        Map<String, Object> response = new HashMap<>();
        try {
            stockService.addRecipeToStockByRecetteId(stockId, recetteId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.put("message", "Recette ajoutée au stock avec succès");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{stockId}/recipes/{recipeId}")
    public ResponseEntity<Void> deleteRecipeFromStock(@PathVariable Long stockId, @PathVariable Long recipeId) {
        stockService.deleteRecipeFromStock(stockId, recipeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{stockId}/recipes/{recipeId}")
    public ResponseEntity<String> updateRecipe(@PathVariable Long stockId, @PathVariable Long recipeId, @RequestBody Recette updatedRecette) {
        stockService.updateRecipe(stockId, recipeId, updatedRecette);
        return ResponseEntity.ok("Recipe updated successfully");
    }


    @PostMapping("/{stockId}/products")
    public ResponseEntity<Void> addProductToStock(@PathVariable Long stockId, @RequestBody Produit produit) {
        stockService.addProductToStock(stockId, produit);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @DeleteMapping("/{stockId}/products/{productId}")
    public ResponseEntity<Void> deleteProductFromStock(@PathVariable Long stockId, @PathVariable Long productId) {
        stockService.deleteProductFromStock(stockId, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{stockId}/products/{productId}/delete-course")
    public ResponseEntity<Void> deleteProductFromStockCourse(@PathVariable Long stockId, @PathVariable Long productId) {
        stockService.deleteProductFromStockCourse(stockId, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{stockId}/products/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Long stockId, @PathVariable Long productId, @RequestBody Produit updatedProduit) {
        stockService.updateProduct(stockId, productId, updatedProduit);
        return ResponseEntity.ok("Product updated successfully");
    }


    @GetMapping("/{stockId}/products")
    public List<Produit> getAllProductsInStock(@PathVariable Long stockId) {
        return stockService.getAllProductsInStock(stockId);
    }


    @GetMapping("/{stockId}/products/gaspille")
    public List<Produit> getAllProductsInStockGaspille(@PathVariable Long stockId) {
        return stockService.getAllProductsInStockGapille(stockId);
    }



    @GetMapping("/{stockId}/recipes")
    public ResponseEntity<List<Recette>> getAllRecipesInStock(@PathVariable Long stockId) {
        List<Recette> recipes = stockService.getAllRecipesInStock(stockId);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("/{stockId}")
    public Stock getStockById(@PathVariable Long stockId) {
        return stockService.getStock(stockId);
    }

    @PutMapping("/{stockId}/{newQuantiteCritiqueParDefault}")
    public ResponseEntity<?> updateQuantiteCritiqueParDefaut(@PathVariable Long stockId, @PathVariable int newQuantiteCritiqueParDefault){
        Map<String, Object> response = new HashMap<>();
        try{
            stockService.updateQuantiteCritiqueParDefaut(stockId, newQuantiteCritiqueParDefault);
        }catch(Exception error){
            response.put("Error", error);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "QuantiteCritiqueParDefault du stock with stock_id="+stockId+" updated successfully!...");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/{stockId}/budget")
    public ResponseEntity<List<Double>> getPricesBudget (@PathVariable Long stockId){
        List<Double> budget = new ArrayList<>();
        Double value1=budgetRepository.PriceGaspilleProduits(stockId);
        Double value2=budgetRepository.PriceNonGaspilleProduits(stockId);
        budget.add(value1);
        budget.add(value2);
        budget.add(value1+value2);
        budget.add((value1*100)/(value1+value2));
        budget.add((value2*100)/(value1+value2));

        return new ResponseEntity<List<Double>>(budget, HttpStatus.OK);
    }
    @PutMapping("/{stockId}/suppProduit/{produitId}")
    public  ResponseEntity<?> updateDeleteProductsInStock(@PathVariable Long stockId, @PathVariable Long produitId) {
        produitRepository.updateDeleteProductsInStock(stockId, produitId);
        return new ResponseEntity<>("Product marked as deleted in stock.", HttpStatus.OK);
    }

    @PutMapping("/{stockId}/suppRepas/{repasId}")
    public  ResponseEntity<?> updateDeleteRepasInStock(@PathVariable Long stockId, @PathVariable Long repasId) {
        repasRepository.updateDeleteRepasInStock(stockId, repasId);
        return new ResponseEntity<>("Product marked as deleted in stock.", HttpStatus.OK);
    }
}



class RequestBodyData {


    private String intitule;
    private String datePeremtion;
    private String dateAlert;
    private String stock;

    private String categorie;
    private List<Produit> arraylist_of_product;
    private String spinnerText;

    public RequestBodyData(String intitule, String datePeremtion, String dateAlert, String hello,String categorie, List<Produit> arraylist_of_product, String spinnerText) {
        this.intitule = intitule;
        this.datePeremtion = datePeremtion;
        this.dateAlert = dateAlert;
        this.stock = hello;
        this.categorie = categorie;
        this.arraylist_of_product = arraylist_of_product;
        this.spinnerText = spinnerText;

    }

    public String getIntitule() {
        return intitule;
    }

    public String getDatePeremtion() {
        return datePeremtion;
    }

    public String getDateAlert() {
        return dateAlert;
    }

    public String getStock() {
        return stock;
    }

    public String getCategorie() {
        return categorie;
    }

    public List<Produit> getArraylist_of_product() {
        return arraylist_of_product;
    }

    public String getSpinnerText() {
        return spinnerText;
    }




}

class RequestBodyDataRepas{

    private String id;

    private String intitule;
    private String datePeremtion;
    private String dateAlert;
    private String spinnerText;


    public RequestBodyDataRepas(String intitule, String datePeremtion, String dateAlert,String categorie,String id) {
        this.id = id;
        this.intitule = intitule;
        this.datePeremtion = datePeremtion;
        this.dateAlert = dateAlert;
        this.spinnerText = categorie;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getDatePeremtion() {
        return datePeremtion;
    }

    public void setDatePeremtion(String datePeremtion) {
        this.datePeremtion = datePeremtion;
    }

    public String getDateAlert() {
        return dateAlert;
    }

    public void setDateAlert(String dateAlert) {
        this.dateAlert = dateAlert;
    }

    public String getSpinnerText() {
        return spinnerText;
    }

    public void setSpinnerText(String spinnerText) {
        this.spinnerText = spinnerText;
    }
}


