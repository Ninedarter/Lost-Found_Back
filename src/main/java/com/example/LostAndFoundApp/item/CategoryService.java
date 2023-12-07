package com.example.LostAndFoundApp.item;

import com.example.LostAndFoundApp.item.coordinates.CoordinatesRepository;
import com.example.LostAndFoundApp.item.found.FoundItem;
import com.example.LostAndFoundApp.item.Item;
import com.example.LostAndFoundApp.item.found.FoundItemRepository;
import com.example.LostAndFoundApp.mapping.MappingService;
import jakarta.persistence.Enumerated;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final FoundItemRepository foundItemRepository;
    private final MappingService mappingService;

    public List<ItemCategory> getCategoryList() {
        return Arrays.asList(ItemCategory.values());
    }
}


