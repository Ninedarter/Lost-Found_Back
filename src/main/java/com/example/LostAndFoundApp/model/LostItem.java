package com.example.LostAndFoundApp.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "lost_items")
@AllArgsConstructor
@NoArgsConstructor
public class LostItem implements Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private ItemCategory category;

    @NonNull
    private String title;


    @NonNull
    private LocalDate dateLost;

    @Nullable
    private String description;

    @NonNull
    private LocalDateTime creationTime;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "loser_id")
    private User user;


    @OneToOne
    @JoinColumn(name = "coordinates_id")
    private Coordinates coordinatesId;

    public LostItem(@NonNull ItemCategory category, @NonNull String title, @NonNull Coordinates coordinatesId, @NonNull LocalDate dateLost, String description, @NonNull LocalDateTime creationTime, @NonNull User user) {
        this.category = category;
        this.title = title;
        this.coordinatesId = coordinatesId;
        this.dateLost = dateLost;
        this.description = description;
        this.creationTime = creationTime;
        this.user = user;
    }


}
