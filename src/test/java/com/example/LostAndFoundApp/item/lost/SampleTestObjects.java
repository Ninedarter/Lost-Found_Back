package com.example.LostAndFoundApp.item.lost;

import com.example.LostAndFoundApp.item.ItemCategory;
import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.item.found.FoundItem;
import com.example.LostAndFoundApp.item.lost.request.LostItemRequest;
import com.example.LostAndFoundApp.item.lost.request.LostItemRequestAdd;
import com.example.LostAndFoundApp.user.Gender;
import com.example.LostAndFoundApp.user.Role;
import com.example.LostAndFoundApp.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SampleTestObjects {

    public static LostItemRequest createLostItemRequest() {
        return LostItemRequest.builder()
                .email("test@example.com")
                .id(1L)
                .category(ItemCategory.KEYS)
                .title("Test Item")
                .dateLost(LocalDate.now())
                .description("Sample description")
                .coordinates(createCoordinates())
                .latitude(40.7128)
                .longitude(-74.0060)
                .build();
    }

    public static User createUser() {
        return User.builder()
                .firstname("Jacob")
                .lastname("Johnson")
                .email("jacob.johnson@gmail.com")
                .phoneNumber("+37068898744")
                .dob(LocalDate.of(1995, 8, 10))
                .gender(Gender.MALE)
                .password("password123")
                .role(Role.USER).build();

    }

    public static LostItem createLostItem(User user, Coordinates coordinates) {
        return LostItem.builder()
                .id(1L)
                .category(ItemCategory.KEYS)
                .title("Keys found in the park")
                .dateLost(LocalDate.now())
                .description(" ")
                .creationTime(LocalDateTime.now())
                .user(user)
                .coordinates(coordinates)
                .build();
    }

    public static List<LostItem> createLostItemsWithDifferentUsers() {
        User user1 = createUser("test@example.com");
        User user2 = createUser("anotheruser@example.com");

        List<LostItem> lostItems = new ArrayList<>();
        lostItems.add(createLostItem(user1, createCoordinates()));
        lostItems.add(createLostItem(user2, createCoordinates()));

        return lostItems;
    }

    private static User createUser(String email) {
        return User.builder()
                .firstname("Sample")
                .lastname("User")
                .email(email)
                .phoneNumber("+1234567890")
                .dob(LocalDate.of(1990, 1, 1))
                .gender(Gender.MALE)
                .password("password123")
                .role(Role.USER)
                .build();
    }


    public static Coordinates createCoordinates() {
        return new Coordinates(40.7128, -74.0060);
    }

    public static LostItemRequestAdd createLostItemRequestAdd() {
        LostItemRequestAdd request = new LostItemRequestAdd();
        request.setCategory(ItemCategory.KEYS);
        request.setTitle("Sample Title");
        request.setDateLost(LocalDate.now());
        request.setDescription("Sample Description");
        request.setCoordinates(new Coordinates(10.00,20.11));
        request.setReward("Sample Reward");
        return request;
    }
}
