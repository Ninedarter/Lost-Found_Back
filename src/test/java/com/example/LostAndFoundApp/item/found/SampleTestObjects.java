package com.example.LostAndFoundApp.item.found;

import com.example.LostAndFoundApp.item.ItemCategory;
import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.item.found.request.FoundItemRequest;
import com.example.LostAndFoundApp.user.Gender;
import com.example.LostAndFoundApp.user.Role;
import com.example.LostAndFoundApp.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SampleTestObjects {

    public static FoundItemRequest createFoundItemRequest() {
        return FoundItemRequest.builder()
                .email("test@example.com")
                .id(1L)
                .category(ItemCategory.KEYS)
                .title("Test Item")
                .dateFound(LocalDate.now())
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

    public static FoundItem createFoundItem(User user, Coordinates coordinates) {
        return FoundItem.builder()
                .id(1L)
                .category(ItemCategory.KEYS)
                .title("Keys found in the park")
                .dateFound(LocalDate.now())
                .description(" ")
                .creationTime(LocalDateTime.now())
                .user(user)
                .coordinates(coordinates)
                .build();
    }


    public static Coordinates createCoordinates() {
        return new Coordinates(40.7128, -74.0060);
    }
}
