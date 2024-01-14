package com.example.stockifybackend.Entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Repas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String intitule;

    private Date datePeremtion;
    private Date dateAlert;


    private String categories;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @OneToMany
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
    public List<Ingredient> ingredients;

    private int is_deleted = 0;
    private int permanent = 0;
    private int gaspille = 0;
    private String imageUrl;

    public boolean isExpired() {
        return this.datePeremtion != null && new Date().after(this.datePeremtion);
    }



    public boolean isCloseToExpired() {
        return this.datePeremtion != null && new Date().after(this.dateAlert);
    }

    public long getDaysBetweenAlertAndExpiration() {
        if (dateAlert != null && datePeremtion != null) {
            LocalDate alertDate = dateAlert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate expirationDate = datePeremtion.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


            return Duration.between(alertDate.atStartOfDay(), expirationDate.atStartOfDay()).toDays();
        }
        return -1;}




}

