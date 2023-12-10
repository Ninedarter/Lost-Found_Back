package com.example.LostAndFoundApp.item.lost;

import com.example.LostAndFoundApp.item.ItemCategory;
import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.item.lost.request.LostItemRequestAdd;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LostItemRequestAddTest {

    @Test
    void testBuilder() {
        LostItemRequestAdd lostItemRequest = LostItemRequestAdd.builder()
                .category(ItemCategory.KEYS)
                .title("Lost Phone")
                .dateLost(LocalDate.now())
                .description("Black smartphone lost in the city center.")
                .coordinates(new Coordinates(40.7128, -74.0060))
                .reward("100")
                .build();

        assertNotNull(lostItemRequest);
        assertEquals(ItemCategory.KEYS, lostItemRequest.getCategory());
        assertEquals("Lost Phone", lostItemRequest.getTitle());
        assertEquals(LocalDate.now(), lostItemRequest.getDateLost());
        assertEquals("Black smartphone lost in the city center.", lostItemRequest.getDescription());
        assertEquals(new Coordinates(40.7128, -74.0060), lostItemRequest.getCoordinates());
        assertEquals("100", lostItemRequest.getReward());
    }

    @Test
    void testNoArgsConstructor() {
        LostItemRequestAdd lostItemRequest = new LostItemRequestAdd();

        assertNotNull(lostItemRequest);
    }
}
