package com.example.LostAndFoundApp.auth;

import com.example.LostAndFoundApp.user.Gender;
import com.example.LostAndFoundApp.user.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegisterRequestTest {

    @Test
    public void testDefaultConstructor() {
        RegisterRequest request = new RegisterRequest();
        Assertions.assertNotNull(request);
    }

    @Test
    public void testAllArgsConstructor() {
        RegisterRequest request = new RegisterRequest("John", "Doe", "john@example.com", "password", Gender.MALE, "2000-01-01", Role.USER, "1234567890");
        Assertions.assertNotNull(request);
    }
}
