package com.example.LostAndFoundApp.config;


import com.example.LostAndFoundApp.item.ItemCategory;
import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.item.coordinates.CoordinatesRepository;
import com.example.LostAndFoundApp.item.found.FoundItem;
import com.example.LostAndFoundApp.item.found.FoundItemRepository;
import com.example.LostAndFoundApp.item.lost.LostItemRepository;
import com.example.LostAndFoundApp.user.Gender;
import com.example.LostAndFoundApp.user.User;
import com.example.LostAndFoundApp.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class AppConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, LostItemRepository lostItemRepository, FoundItemRepository foundItemRepository, CoordinatesRepository coordinatesRepository) {
        return args -> {
            User user222 = new User(
                    "Alice",
                    "Johnson",
                    "user1@example.com",
                    LocalDate.of(1995, 3, 10),
                    Gender.FEMALE,
                    "1234",
                    null,
                    null
            );
            userRepository.save(user222);

            Coordinates item222Coordinates = new Coordinates(37.7749, -122.4194);
            coordinatesRepository.save(item222Coordinates);

            FoundItem foundItem222 = new FoundItem(
                    ItemCategory.GLASSES,
                    "Found Sunglasses",
                    item222Coordinates,
                    LocalDate.now().minusDays(1),
                    "Found sunglasses near the coffee shop.",
                    LocalDateTime.now().minusHours(3),
                    user222
            );
            foundItemRepository.save(foundItem222);

        };
    }
}
