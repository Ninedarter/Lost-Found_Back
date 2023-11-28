package com.example.LostAndFoundApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class LostItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private ItemType type;
    private String title;
    private double latitude;
    private double longitude;
    private LocalDate dateLost;
    private String description;
    private LocalDateTime creationTime;

    public LostItem(long id, ItemType type, String title, double latitude, double longitude, LocalDate dateLost, String description, LocalDateTime creationTime) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateLost = dateLost;
        this.description = description;
        this.creationTime = creationTime;
    }

}
