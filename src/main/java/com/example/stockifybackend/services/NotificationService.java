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
  /*

   @Autowired
    private final FirebaseMessaging firebaseMessaging;

    @Autowired
    public NotificationService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    @Scheduled(fixedRate = 5000)
    public void sendScheduledNotification() {

        String token =" fbZTzOhMREGFKL1bHs7DCN:APA91bGnK3eX2mLaPAUq1e84VlGSyJrfj1mm6qrkarRSIZaduZnjmQuTnb1NV2WdqojJRQaTKNBduggSKTyla2xXUaNF4-oByvy9NRjlogEoPahb8iB67VCutafXKcY8pO7N2Dq0rIYG";
        try {


            Notification notification = Notification
                    .builder()
                    .setTitle("title")
                    .setBody("body")
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
   */
}
