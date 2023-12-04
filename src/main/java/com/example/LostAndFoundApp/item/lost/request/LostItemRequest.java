package com.example.LostAndFoundApp.item.lost.request;

import com.example.LostAndFoundApp.item.ItemCategory;
import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LostItemRequest {

    private String email;
    private Long id;
    private ItemCategory category;
    private String title;
    private LocalDate dateLost;
    private String description;
    private LocalDateTime creationTime;
    private Coordinates coordinates;
    private Double latitude;
    private Double longitude;
}
