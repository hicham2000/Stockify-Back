package com.example.stockifybackend.services;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.example.stockifybackend.Entities.Repas;
import com.example.stockifybackend.Entities.Stock;
import com.example.stockifybackend.Entities.Produit;
import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private StockService stockService;

   @Autowired
    private final FirebaseMessaging firebaseMessaging;

    private final Map<String, Long> lastNotificationTimestamps = new ConcurrentHashMap<>();

    @Autowired
    public NotificationService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    @Scheduled(fixedRate = 1000*5)
    @Transactional
    public void ReptureStockNotification() {


        List<Stock> stocks = stockService.getAllStocks();
        for (Stock stock : stocks) {

            List<Produit> belowCriticalProducts = stock.getProduit().stream()
                    .filter(Produit::isQuantityBelowCritical)
                    .toList();

            if (!belowCriticalProducts.isEmpty()) {

                String userNotificationToken = stock.getUtilisateur().getNotifToken();
                long lastNotificationTimestamp = lastNotificationTimestamps.getOrDefault(userNotificationToken, 0L);

                if (System.currentTimeMillis() - lastNotificationTimestamp > 86400000) {
                    String productNames = belowCriticalProducts.stream()
                            .map(Produit::getIntitule)
                            .collect(Collectors.joining(", "));
                    String notificationMessage = "Products below critical quantity: " + productNames;

                    sendNotification(userNotificationToken, notificationMessage, "Repture de Stock");

                    lastNotificationTimestamps.put(userNotificationToken, System.currentTimeMillis());
                }
            }
        }
            }

    public void sendNotification(String token,String body,String title){

        try {
            if(token != null){
            Notification notification = Notification
                    .builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();
            Message message = Message
                    .builder()
                    .setToken(token)
                    .setNotification(notification)
                    .build();

            String response = firebaseMessaging.send(message);
            System.out.println("Successfully sent scheduled message: " + response);}
        } catch (FirebaseMessagingException e) {

            throw new RuntimeException(e);
        }

    }

    @Scheduled(cron = "0 1 0 * * ?")
    @Transactional
    public void ExprirationProduitAlert() {

        List<Stock> stocks = stockService.getAllStocks();

        for (Stock stock : stocks) {
            List<Produit> toExpiredProducts = stock.getProduit().stream()
                    .filter(Produit::isCloseToExpired)
                    .toList();

            if (!toExpiredProducts.isEmpty()) {

                String userNotificationToken = stock.getUtilisateur().getNotifToken();
                for (Produit produit : toExpiredProducts) {

                    long daysUntilExpiration = produit.getDaysBetweenAlertAndExpiration();


                    String notificationMessage = produit.getIntitule() +
                            "expires in " + daysUntilExpiration + " days.";

                    sendNotification(userNotificationToken, notificationMessage, "Expiration alert");
                }
            }
        }

    }

    @Scheduled(cron = "0 1 0 * * ?")
    @Transactional
    public void ExprirationProduit() {
        List<Stock> stocks = stockService.getAllStocks();
        for (Stock stock : stocks) {
            List<Produit> toExpiredProducts = stock.getProduit().stream()
                    .filter(Produit::isExpired)
                    .toList();

            if (!toExpiredProducts.isEmpty()) {

                String userNotificationToken = stock.getUtilisateur().getNotifToken();
                for (Produit produit : toExpiredProducts) {
                    produit.setIs_deleted(1);
                    produit.setGaspille(1);
                    String notificationMessage = produit.getIntitule() +
                            "is expired ";
                    sendNotification(userNotificationToken, notificationMessage, "Expired produits");
                }
            }
        }
    }


    @Scheduled(cron = "0 1 0 * * ?")
    @Transactional
    public void ExprirationRepas() {
        List<Stock> stocks = stockService.getAllStocks();
        for (Stock stock : stocks) {
            List<Repas> toExpiredRepas= stock.getRepas().stream()
                    .filter(Repas::isExpired)
                    .toList();

            if (!toExpiredRepas.isEmpty()) {

                String userNotificationToken = stock.getUtilisateur().getNotifToken();
                for (Repas repas: toExpiredRepas) {
                    repas.setIs_deleted(1);
                    repas.setGaspille(1);
                    String notificationMessage = repas.getIntitule() +
                            "is expired ";
                    sendNotification(userNotificationToken, notificationMessage, "Expired produits");
                }
            }
        }
    }

    @Scheduled(cron = "0 1 0 * * ?")
    @Transactional
    public void ExprirationRepasAlert() {

        List<Stock> stocks = stockService.getAllStocks();

        for (Stock stock : stocks) {
            List<Repas> toExpiredRepas= stock.getRepas().stream()
                    .filter(Repas::isCloseToExpired)
                    .toList();

            if (!toExpiredRepas.isEmpty()) {

                String userNotificationToken = stock.getUtilisateur().getNotifToken();
                for (Repas repas: toExpiredRepas) {

                    long daysUntilExpiration = repas.getDaysBetweenAlertAndExpiration();


                    String notificationMessage = repas.getIntitule() +
                            "expires in " + daysUntilExpiration + " days.";

                    sendNotification(userNotificationToken, notificationMessage, "Expiration alert");
                }
            }
        }

    }



}
