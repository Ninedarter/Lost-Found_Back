package com.example.LostAndFoundApp.item.lost;

import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.item.Item;
import com.example.LostAndFoundApp.item.ItemCategory;
import com.example.LostAndFoundApp.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;



@Data
@Builder
@Entity
@Table(name = "lost_items")
@AllArgsConstructor
@NoArgsConstructor
public class LostItem implements Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
//    @JsonIgnore
    private User user;


    @OneToOne
    @JoinColumn(name = "coordinates_id")
//    @JsonIgnore
    private Coordinates coordinates;

    public LostItem(@NonNull ItemCategory category, @NonNull String title, @NonNull Coordinates coordinates, @NonNull LocalDate dateLost, String description, @NonNull LocalDateTime creationTime, @NonNull User user) {
        this.category = category;
        this.title = title;
        this.coordinates = coordinates;
        this.dateLost = dateLost;
        this.description = description;
        this.creationTime = creationTime;
        this.user = user;
    }


    @Override
    public String toString() {
        return "LostItem{" +
                "id=" + id +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", dateLost=" + dateLost +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", coordinates=" + coordinates +
                '}';
    }
}
