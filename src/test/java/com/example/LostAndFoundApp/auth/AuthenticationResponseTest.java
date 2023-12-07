package com.example.LostAndFoundApp.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AuthenticationResponseTest {

    @Test
    @DisplayName("Create AuthenticationResponse with valid values")
    void createAuthenticationResponseWithValidValues() {
        String accessToken = "sampleAccessToken";
        String refreshToken = "sampleRefreshToken";

        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        Assertions.assertEquals(accessToken, authenticationResponse.getAccessToken());
        Assertions.assertEquals(refreshToken, authenticationResponse.getRefreshToken());
    }

}
