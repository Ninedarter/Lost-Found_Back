package com.example.LostAndFoundApp.item.lost;

import com.example.LostAndFoundApp.item.ItemCategory;
import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LostItemTest {

    @Test
    @DisplayName("Create LostItem with Reward and CreationTime")
    public void testCreateLostItemWithRewardAndCreationTime() {

        ItemCategory category = ItemCategory.CLOTHES;
        String title = "Lost Clothing";
        Coordinates coordinates = new Coordinates(1.0, 2.0);
        LocalDate dateLost = LocalDate.now();
        String description = "Lost clothing description";
        String reward = "50 USD";
        LocalDateTime creationTime = LocalDateTime.now();
        User user = new User();

        LostItem lostItem = new LostItem(category, title, coordinates, dateLost, description, reward, creationTime, user);

        Assertions.assertNotNull(lostItem);
        Assertions.assertEquals(category, lostItem.getCategory());
        Assertions.assertEquals(title, lostItem.getTitle());
        Assertions.assertEquals(coordinates, lostItem.getCoordinates());
        Assertions.assertEquals(dateLost, lostItem.getDateLost());
        Assertions.assertEquals(description, lostItem.getDescription());
        Assertions.assertEquals(reward, lostItem.getReward());
        Assertions.assertEquals(creationTime, lostItem.getCreationTime());
        Assertions.assertEquals(user, lostItem.getUser());
    }

}
