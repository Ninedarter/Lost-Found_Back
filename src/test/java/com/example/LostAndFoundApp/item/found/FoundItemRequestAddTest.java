package com.example.LostAndFoundApp.item.found;

import com.example.LostAndFoundApp.item.ItemCategory;
import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.item.found.request.FoundItemRequestAdd;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FoundItemRequestAddTest {

    @Test
    void testBuilder() {
        FoundItemRequestAdd foundItemRequestAdd = FoundItemRequestAdd.builder()
                .title("Test Item")
                .description("Test Description")
                .category(ItemCategory.WALLET)
                .dateFound(LocalDate.now())
                .coordinates(new Coordinates(10.0, 20.0))
                .imageUrl("testImageUrl")
                .build();

        assertNotNull(foundItemRequestAdd);
        assertEquals("Test Item", foundItemRequestAdd.getTitle());
        assertEquals("Test Description", foundItemRequestAdd.getDescription());
        assertEquals(ItemCategory.WALLET, foundItemRequestAdd.getCategory());
        assertEquals(LocalDate.now(), foundItemRequestAdd.getDateFound());
        assertEquals(new Coordinates(10.0, 20.0), foundItemRequestAdd.getCoordinates());
        assertEquals("testImageUrl", foundItemRequestAdd.getImageUrl());
    }

    @Test
    void testNoArgsConstructor() {
        FoundItemRequestAdd foundItemRequestAdd = new FoundItemRequestAdd();

        assertNotNull(foundItemRequestAdd);
    }
}
