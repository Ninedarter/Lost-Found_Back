package com.example.LostAndFoundApp.item.found.request;

import com.example.LostAndFoundApp.item.ItemCategory;
import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoundItemRequestEasier {
    private String title;
    private String description;
    private ItemCategory category;
    private LocalDate dateFound;
    private Coordinates coordinates;
    private String imageUrl;
}
