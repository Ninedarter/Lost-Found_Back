package com.example.LostAndFoundApp.repository;

import com.example.LostAndFoundApp.config.TestConfig;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private User savedUser;

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
    }

    @Test
    @DisplayName("Save User: Returns Saved User with ID > 0")
    public void save_returnSavedUser() {

        Assertions.assertNotNull(savedUser);
        Assertions.assertTrue(savedUser.getId() > 0);
    }

    @Test
    @DisplayName("Get All Users: Returns More Than One User")
    public void getAll_returnMoreThanOneUser() {

        User user2 = User.builder()
                .firstname("Jacob")
                .lastname("Johnson")
                .email("jacob.johnson@gmail.com")
                .phoneNumber("+37068898744")
                .dob(LocalDate.of(1995, 8, 10))
                .gender(Gender.MALE)
                .password("password123")
                .role(Role.USER).build();

        userRepository.save(user2);
        List<User> userList = userRepository.findAll();

        Assertions.assertEquals(2, userList.size());
    }

    @Test
    @DisplayName("Save user: find user by ID")
    public void findByID_returnUser() {

        User retrievedUser = userRepository.findById(savedUser.getId()).orElse(null);

        Assertions.assertEquals(savedUser.getId(), retrievedUser.getId(), "IDs should match");
        Assertions.assertEquals(savedUser.getEmail(), retrievedUser.getEmail(), "Emails should match");
    }

    @Test
    @DisplayName("Update user and verify changes")
    public void updateUser_returnUpdatedUser() {

        User updatedUser = User.builder()
                .id(savedUser.getId())
                .firstname("UpdatedJacob")
                .lastname("Johnson")
                .email("jacob.johnson@gmail.com")
                .phoneNumber("+311111111")
                .dob(LocalDate.of(1995, 8, 10))
                .gender(Gender.MALE)
                .password("password123")
                .build();

        userRepository.save(updatedUser);
        User retrievedUser = userRepository.findById(savedUser.getId()).orElse(null);

        Assertions.assertEquals("UpdatedJacob", retrievedUser.getFirstname(), "First names should match");
        Assertions.assertEquals("+311111111", retrievedUser.getPhoneNumber(), "Phone numbers should match");
    }

    @Test
    @DisplayName("Delete User: Repository Should Not Contain Deleted User")
    public void deleteUser_returnUserIsEmpty() {

        userRepository.deleteById(savedUser.getId());

        Optional<User> deletedUser = userRepository.findById(savedUser.getId());

        Assertions.assertTrue(deletedUser.isEmpty());

    }

    @Test
    @DisplayName("Find User by Existing Email: Returns User")
    public void findByEmail_withExistingEmail_returnsUser() {

        Optional<User> foundUser = userRepository.findByEmail(savedUser.getEmail());
        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals(savedUser.getEmail(), foundUser.get().getEmail());
    }

    @Test
    @DisplayName("Find User by Non-Existing Email: Returns Empty Optional")
    public void findByEmail_withNonExistingEmail_returnsEmptyOptional() {

        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");
        Assertions.assertTrue(foundUser.isEmpty());
    }

    @Test
    @DisplayName("Save User with User Role: Has User Role Authorities")
    public void saveUser_withUserRole_hasUserRoleAuthorities() {

        Assertions.assertTrue(savedUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    @DisplayName("Save User with ADMIN Role: Has ADMIN Role Authorities")
    public void saveUser_withAdminRole_hasAdminRoleAuthorities() {
        User adminUser = User.builder()
                .firstname("Admin")
                .lastname("User")
                .email("admin@example.com")
                .phoneNumber("+1234567890")
                .dob(LocalDate.of(1990, 1, 1))
                .gender(Gender.MALE)
                .password("adminPassword")
                .role(Role.ADMIN).build();

        User savedAdminUser = userRepository.save(adminUser);

        Assertions.assertTrue(savedAdminUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }


}
