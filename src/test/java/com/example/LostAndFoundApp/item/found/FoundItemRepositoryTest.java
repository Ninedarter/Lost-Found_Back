package com.example.LostAndFoundApp.item.found;

import com.example.LostAndFoundApp.config.TestConfig;
import com.example.LostAndFoundApp.item.ItemCategory;
import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.item.found.FoundItem;
import com.example.LostAndFoundApp.user.Gender;
import com.example.LostAndFoundApp.user.Role;
import com.example.LostAndFoundApp.user.User;
import com.example.LostAndFoundApp.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
public class FoundItemRepositoryTest {

    @Autowired
    private com.example.LostAndFoundApp.item.found.FoundItemRepository FoundItemRepository;
    @Autowired
    private UserRepository userRepository;
    private Coordinates coordinates;
    private User savedUser;
    private FoundItem savedFoundItem;

    @BeforeEach
    void setUp() {

        User user = User.builder()
                .firstname("Jacob")
                .lastname("Johnson")
                .email("jacob.johnson@gmail.com")
                .phoneNumber("+37068898744")
                .dob(LocalDate.of(1995, 8, 10))
                .gender(Gender.MALE)
                .password("password123")
                .role(Role.USER).build();
        savedUser = userRepository.save(user);

        FoundItem foundItem = FoundItem.builder()
                .category(ItemCategory.KEYS)
                .title("Keys found in the park")
                .dateFound(LocalDate.of(2023, 11, 30))
                .description(" ")
                .creationTime(LocalDateTime.now())
                .user(savedUser)
                .coordinates(coordinates)
                .build();

        savedFoundItem = FoundItemRepository.save(foundItem);
    }

    @Test
    @DisplayName("Save found item: Returns saved found item")
    public void saveFoundItem_ReturnSavedFoundItem() {
        Assertions.assertNotNull(savedFoundItem);
        Assertions.assertEquals(ItemCategory.KEYS, savedFoundItem.getCategory());
        Assertions.assertEquals("Keys found in the park", savedFoundItem.getTitle());
    }

    @Test
    @DisplayName("Save found item: find item by ID")
    public void findById() {
        FoundItem retrievedFoundItem = FoundItemRepository.findById(savedFoundItem.getId()).orElse(null);

        Assertions.assertEquals(savedFoundItem.getId(), retrievedFoundItem.getId());
        Assertions.assertEquals(savedFoundItem.getTitle(), retrievedFoundItem.getTitle());
    }

    @Test
    @DisplayName("Update found item: Returns updated found item")
    public void updateFoundItem_ReturnUpdatedFoundItem() {
        savedFoundItem.setTitle("Updated Title");
        savedFoundItem.setDescription("Updated Description");

        FoundItem updatedFoundItem = FoundItemRepository.save(savedFoundItem);

        Assertions.assertNotNull(updatedFoundItem);
        Assertions.assertEquals("Updated Title", updatedFoundItem.getTitle());
        Assertions.assertEquals("Updated Description", updatedFoundItem.getDescription());
    }

    @Test
    @DisplayName("Delete found item: Returns null after deletion")
    public void deleteFoundItem_ReturnNullAfterDeletion() {

        FoundItemRepository.deleteById(savedFoundItem.getId());

        FoundItem deletedFoundItem = FoundItemRepository.findById(savedFoundItem.getId()).orElse(null);

        Assertions.assertNull(deletedFoundItem);
    }
}
