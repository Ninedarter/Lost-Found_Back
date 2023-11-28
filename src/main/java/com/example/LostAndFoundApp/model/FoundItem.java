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
public class FoundItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private ItemType type;
    private double latitude;
    private double longitude;
    private String title;
    private String description;
    private LocalDate dateFound;
    private LocalDateTime creationTime;

    public FoundItem(long id, ItemType type, double latitude, double longitude, String title, String description, LocalDate dateFound, LocalDateTime creationTime) {
        this.id = id;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.description = description;
        this.dateFound = dateFound;
        this.creationTime = creationTime;
    }

}
