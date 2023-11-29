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
@Table(name = "found_items")
public class FoundItem implements Item {

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
    private LocalDate dateFound;

    @Nullable
    private String description;

    @NonNull
    private LocalDateTime creationTime;

    public FoundItem(long id, @NonNull ItemCategory category, @NonNull String title, @NonNull Coordinates coordinates, @NonNull LocalDate dateFound, @Nullable String description, @NonNull LocalDateTime creationTime) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.coordinates = coordinates;
        this.dateFound = dateFound;
        this.description = description;
        this.creationTime = creationTime;
    }
}
