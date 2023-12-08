package com.example.LostAndFoundApp.item.lost.request;

import com.example.LostAndFoundApp.item.ItemCategory;
import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LostItemRequestAdd {
    private ItemCategory category;
    private String title;
    private LocalDate dateLost;
    private String description;
    private Coordinates coordinates;

    @Nullable
    private String reward;
}
