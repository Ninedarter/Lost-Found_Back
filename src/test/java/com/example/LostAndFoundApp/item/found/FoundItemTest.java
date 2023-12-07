package com.example.LostAndFoundApp.item.found;

import com.example.LostAndFoundApp.item.ItemCategory;
import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FoundItemTest {

    @Test
    void testAllArgsConstructor() {

        ItemCategory category = ItemCategory.KEYS;
        Coordinates coordinates = new Coordinates(10.0, 20.0);
        LocalDate dateFound = LocalDate.now();
        LocalDateTime creationTime = LocalDateTime.now();
        User user = new User();

        FoundItem foundItem = new FoundItem(category, "TestTitle", coordinates, dateFound, "TestDescription", creationTime, user);

        Assertions.assertEquals(category, foundItem.getCategory());
        Assertions.assertEquals("TestTitle", foundItem.getTitle());
        Assertions.assertEquals(coordinates, foundItem.getCoordinates());
        Assertions.assertEquals(dateFound, foundItem.getDateFound());
        Assertions.assertEquals("TestDescription", foundItem.getDescription());
        Assertions.assertEquals(creationTime, foundItem.getCreationTime());
        Assertions.assertEquals(user, foundItem.getUser());
    }

    @Test
    void testNoArgsConstructor() {
        FoundItem foundItem = new FoundItem();
        Assertions.assertNotNull(foundItem);
    }

    @Test
    void testDescriptionConstructor() {
        FoundItem foundItem = new FoundItem("TestDescription");

        Assertions.assertEquals("TestDescription", foundItem.getDescription());
    }

}
