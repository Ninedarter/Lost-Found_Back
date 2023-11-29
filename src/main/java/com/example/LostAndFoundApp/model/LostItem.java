package com.example.LostAndFoundApp.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "lost_items")
public class LostItem implements Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private ItemCategory category;

    @NonNull
    private String title;

    @NonNull
    private Coordinates coordinates;

    @NonNull
    private LocalDate dateLost;

    @Nullable
    private String description;

    @NonNull
    private LocalDateTime creationTime;

    public LostItem(long id, @NonNull ItemCategory category, @NonNull String title, @NonNull Coordinates coordinates, @NonNull LocalDate dateLost, @Nullable String description, @NonNull LocalDateTime creationTime) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.coordinates = coordinates;
        this.dateLost = dateLost;
        this.description = description;
        this.creationTime = creationTime;
    }
}
