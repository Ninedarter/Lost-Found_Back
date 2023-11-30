package com.example.LostAndFoundApp.item.found;

import com.example.LostAndFoundApp.item.ItemCategory;
import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoundItemRequest {


    @NonNull
    private String email;
    @NonNull
    private Long id;
    @NonNull
    private ItemCategory category;
    @NonNull
    private String title;
    @NonNull
    private LocalDate dateFound;
    @NonNull
    private String description;
    @NonNull
    private LocalDateTime creationTime;
    @NonNull
    private Coordinates coordinates;


    @NonNull
    private Double latitude;
    @NonNull
    private Double longitude;
}
