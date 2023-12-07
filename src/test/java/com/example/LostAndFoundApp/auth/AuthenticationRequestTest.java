package com.example.LostAndFoundApp.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AuthenticationRequestTest {

    @Test
    @DisplayName("Create AuthenticationRequest with valid values")
    void createAuthenticationRequestWithValidValues() {
        String email = "test@example.com";
        String password = "password";

        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email(email)
                .password(password)
                .build();

        Assertions.assertEquals(email, authenticationRequest.getEmail());
        Assertions.assertEquals(password, authenticationRequest.getPassword());
    }

}
