package com.example.LostAndFoundApp.auth;

import com.example.LostAndFoundApp.user.Gender;
import com.example.LostAndFoundApp.user.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RegisterRequestTest {

    @Test
    public void testDefaultConstructor() {
        RegisterRequest request = new RegisterRequest();
        assertNotNull(request);
    }

    @Test
    public void testAllArgsConstructor() {
        RegisterRequest request = new RegisterRequest("John", "Doe", "john@example.com", "password", Gender.MALE, "2000-01-01", Role.USER, "1234567890");
        assertNotNull(request);
    }

    @Test
    void testBuilder() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .gender(Gender.MALE)
                .dob("2000-01-01")
                .role(Role.USER)
                .phoneNumber("123456789")
                .build();

        assertNotNull(registerRequest);
        assertEquals("John", registerRequest.getFirstname());
        assertEquals("Doe", registerRequest.getLastname());
        assertEquals("john.doe@example.com", registerRequest.getEmail());
        assertEquals("password123", registerRequest.getPassword());
        assertEquals(Gender.MALE, registerRequest.getGender());
        assertEquals("2000-01-01", registerRequest.getDob());
        assertEquals(Role.USER, registerRequest.getRole());
        assertEquals("123456789", registerRequest.getPhoneNumber());
    }

    @Test
    void testNoArgsConstructor() {
        RegisterRequest registerRequest = new RegisterRequest();

        assertNotNull(registerRequest);
    }
}
