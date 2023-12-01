package com.example.LostAndFoundApp.item.found.request;

import com.example.LostAndFoundApp.item.ItemCategory;
import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoundItemRequest {



    private String email;

    @Nullable
    private Long id;


    private ItemCategory category;
    private String title;
    private LocalDate dateFound;
    private String description;
    private LocalDateTime creationTime;
    private Coordinates coordinates;
    @Nullable
    private Double latitude;
    @Nullable
    private Double longitude;


}
