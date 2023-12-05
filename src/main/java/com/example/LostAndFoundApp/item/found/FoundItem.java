package com.example.LostAndFoundApp.item.found;

import com.example.LostAndFoundApp.item.Item;
import com.example.LostAndFoundApp.item.ItemCategory;
import com.example.LostAndFoundApp.item.coordinates.Coordinates;
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
@Table(name = "found_items")
@AllArgsConstructor
@NoArgsConstructor
public class FoundItem implements Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private ItemCategory category;

    @NonNull
    private String title;

    @NonNull
    private LocalDate dateFound;

    @Nullable
    private String description;

    @NonNull
    private LocalDateTime creationTime;


    @NonNull
    @ManyToOne
    @JoinColumn(name = "finder_id")
    @JsonIgnore
    private User user;


    @OneToOne
    @JoinColumn(name = "coordinates_id")
    @JsonIgnore
    private Coordinates coordinates;


    public FoundItem(@NonNull ItemCategory category, @NonNull String title, @NonNull Coordinates coordinates, @NonNull LocalDate dateFound, String description, @NonNull LocalDateTime creationTime, @NonNull User user) {
        this.category = category;
        this.title = title;
        this.coordinates = coordinates;
        this.dateFound = dateFound;
        this.description = description;
        this.creationTime = creationTime;
        this.user = user;
    }

    public FoundItem(String description) {
        this.description = description;
    }


    public boolean equals(Object obj) {
        FoundItem item = (FoundItem) obj;

        if (title.equals(item.title)) {
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        return "FoundItem{" +
                "id=" + id +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", dateFound=" + dateFound +
                ", description='" + description + '\'' +
                ", creationTime=" + creationTime +
                ", coordinates=" + coordinates +
                '}';
    }
}
