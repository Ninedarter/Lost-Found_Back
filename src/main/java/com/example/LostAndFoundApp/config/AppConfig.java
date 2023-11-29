package com.example.LostAndFoundApp.config;

import com.example.LostAndFoundApp.model.*;
import com.example.LostAndFoundApp.repository.CoordinatesRepository;
import com.example.LostAndFoundApp.repository.FoundItemRepository;
import com.example.LostAndFoundApp.repository.LostItemRepository;
import com.example.LostAndFoundApp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, LostItemRepository lostItemRepository, FoundItemRepository foundItemRepository, CoordinatesRepository coordinatesRepository) {
        return args -> {
            User user222 = new User(
                    "user1@example.com",
                    "Alice",
                    "Johnson",
                    LocalDate.of(1995, 3, 10),
                    Gender.FEMALE,
                    List.of()
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
