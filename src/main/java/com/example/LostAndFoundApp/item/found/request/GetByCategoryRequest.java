package com.example.LostAndFoundApp.item.found.request;


import com.example.LostAndFoundApp.item.ItemCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByCategoryRequest {

    private ItemCategory category;
}
