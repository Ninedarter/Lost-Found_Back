package com.example.LostAndFoundApp.item.lost.request;

import com.example.LostAndFoundApp.item.ItemCategory;
import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LostItemRequest {

    @NonNull
    private String email;
    @NonNull
    private Long id;
    @NonNull
    private ItemCategory category;
    @NonNull
    private String title;
    @NonNull
    private LocalDate dateLost;
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
