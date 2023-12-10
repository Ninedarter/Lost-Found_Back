package com.example.LostAndFoundApp.item.found;

import com.example.LostAndFoundApp.item.ItemCategory;
import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.item.found.request.FoundItemRequest;
import com.example.LostAndFoundApp.item.found.request.FoundItemRequestAdd;
import com.example.LostAndFoundApp.report.ReportUserRequest;
import com.example.LostAndFoundApp.user.ChangePasswordRequest;
import com.example.LostAndFoundApp.user.Gender;
import com.example.LostAndFoundApp.user.Role;
import com.example.LostAndFoundApp.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
                .email("test@example.com")
                .phoneNumber("+37068898744")
                .dob(LocalDate.of(1995, 8, 10))
                .gender(Gender.MALE)
                .password("password123")
                .role(Role.USER).build();

    }

    public static FoundItem createFoundItem(User user, Coordinates coordinates) {
        return FoundItem.builder()
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
        return new Coordinates(1L, 40.7128, -74.0060);
    }

    public static List<FoundItem> createFoundItemsWithDifferentUsers() {
        User user1 = createUser("test@example.com");
        User user2 = createUser("anotheruser@example.com");

        List<FoundItem> foundItems = new ArrayList<>();
        foundItems.add(createFoundItem(user1, createCoordinates()));
        foundItems.add(createFoundItem(user2, createCoordinates()));

        return foundItems;
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

    public static ReportUserRequest createReportUserRequest() {
        ReportUserRequest request = new ReportUserRequest();
        request.setReportedUserId(1L);  // Use user ID instead of email
        request.setDescription("This user is behaving inappropriately.");
        return request;
    }

    public static ChangePasswordRequest createChangePasswordRequest() {
        return ChangePasswordRequest.builder()
                .currentPassword("oldPassword")
                .newPassword("newPassword")
                .confirmationPassword("newPassword")
                .build();
    }

    public static FoundItemRequestAdd createFoundItemRequestAdd() {

        return FoundItemRequestAdd.builder()
                .title("Sample Title")
                .description("Sample Description")
                .category(ItemCategory.CLOTHES)
                .dateFound(LocalDate.now())
                .coordinates(new Coordinates(10.0, 20.0))  // Assuming Coordinates class has a constructor
                .imageUrl("https://example.com/sample-image.jpg")
                .build();

    }
}
