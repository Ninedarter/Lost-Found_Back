package com.example.LostAndFoundApp.item.lost;

import com.example.LostAndFoundApp.config.TestConfig;
import com.example.LostAndFoundApp.item.ItemCategory;
import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.item.lost.LostItem;
import com.example.LostAndFoundApp.item.lost.LostItemRepository;
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
public class LostItemRepositoryTest {

    @Autowired
    private LostItemRepository lostItemRepository;
    @Autowired
    private UserRepository userRepository;
    private Coordinates coordinates;
    private User user;
    private User savedUser;
    private LostItem savedLostItem;

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

        LostItem lostItem = LostItem.builder()
                .category(ItemCategory.KEYS)
                .title("Keys lost in the park")
                .dateLost(LocalDate.of(2023, 11, 30))
                .description(" ")
                .creationTime(LocalDateTime.now())
                .user(savedUser)
                .coordinates(coordinates)
                .build();

        savedLostItem = lostItemRepository.save(lostItem);
    }

    @Test
    @DisplayName("Save lost item: Returns saved lost item")
    public void saveLostItem_ReturnSavedLostItem() {
        Assertions.assertNotNull(savedLostItem);
        Assertions.assertEquals(ItemCategory.KEYS, savedLostItem.getCategory());
        Assertions.assertEquals("Keys lost in the park", savedLostItem.getTitle());
    }

    @Test
    @DisplayName("Save lost item: find item by ID")
    public void findById() {
        LostItem retrievedLostItem = lostItemRepository.findById(savedLostItem.getId()).orElse(null);

        Assertions.assertEquals(savedLostItem.getId(), retrievedLostItem.getId());
        Assertions.assertEquals(savedLostItem.getTitle(), retrievedLostItem.getTitle());
    }

    @Test
    @DisplayName("Update lost item: Returns updated lost item")
    public void updateLostItem_ReturnUpdatedLostItem() {
        savedLostItem.setTitle("Updated Title");
        savedLostItem.setDescription("Updated Description");

        LostItem updatedLostItem = lostItemRepository.save(savedLostItem);

        Assertions.assertNotNull(updatedLostItem);
        Assertions.assertEquals("Updated Title", updatedLostItem.getTitle());
        Assertions.assertEquals("Updated Description", updatedLostItem.getDescription());
    }

    @Test
    @DisplayName("Delete lost item: Returns null after deletion")
    public void deleteLostItem_ReturnNullAfterDeletion() {

        lostItemRepository.deleteById(savedLostItem.getId());

        LostItem deletedLostItem = lostItemRepository.findById(savedLostItem.getId()).orElse(null);

        Assertions.assertNull(deletedLostItem);
    }


}
