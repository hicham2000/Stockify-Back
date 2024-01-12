package com.example.stockifybackend.services;
import java.util.stream.Collectors;
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

    @Autowired
    public NotificationService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    @Scheduled(fixedRate = 5000)
    @Transactional
    public void ReptureStockNotification() {

        List<Stock> stocks = stockService.getAllStocks();

        for (Stock stock : stocks) {
            List<Produit> belowCriticalProducts = stock.getProduit().stream()
                    .filter(Produit::isQuantityBelowCritical)
                    .toList();

            if (!belowCriticalProducts.isEmpty()) {

                String userNotificationToken = stock.getUtilisateur().getNotifToken();
               sendNotification(userNotificationToken, "Products below critical quantity!","Title");
            }
        }

    }
    public void sendNotification(String token,String body,String title){

        try {


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
            System.out.println("Successfully sent scheduled message: " + response);
        } catch (FirebaseMessagingException e) {

            throw new RuntimeException(e);
        }

    }

}
