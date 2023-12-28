package com.example.stockifybackend.services;

import com.example.stockifybackend.Entities.Stock;
import com.example.stockifybackend.Repositories.StockRepository;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationService {

    private final FirebaseMessaging firebaseMessaging;

    @Autowired
    public NotificationService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    @Scheduled(fixedRate = 30000)
    public void sendScheduledNotification() {


        try {
            //LocalDate expiryThreshold = LocalDate.now().plusDays(7);
            //List<Stock> stocksWithExpiringProducts = StockRepository.findStocksWithExpiringProducts(expiryThreshold);

            Message message = Message.builder()
                    .setTopic("product_expiry_alerts")
                    .putData("id_user", "id_user")
                    .putData("body","notification")
                    .build();

            String response = firebaseMessaging.send(message);
            System.out.println("Successfully sent scheduled message: " + response);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }


}
