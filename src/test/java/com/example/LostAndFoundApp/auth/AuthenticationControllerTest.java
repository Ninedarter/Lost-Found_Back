package com.example.LostAndFoundApp.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {

        RegisterRequest registerRequest = new RegisterRequest();
        AuthenticationResponse expectedResponse = new AuthenticationResponse();

        when(authenticationService.register(registerRequest)).thenReturn(expectedResponse);

        ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.register(registerRequest);

        verify(authenticationService, times(1)).register(registerRequest);

    }

    @Test
    public void testAuthenticate() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("username", "password");
        AuthenticationResponse expectedResponse = new AuthenticationResponse();

        when(authenticationService.authenticate(authenticationRequest)).thenReturn(expectedResponse);

        ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.authenticate(authenticationRequest);

        verify(authenticationService, times(1)).authenticate(authenticationRequest);
    }

    @Test
    public void testRefreshToken() throws IOException {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        authenticationController.refreshToken(request, response);

        verify(authenticationService, times(1)).refreshToken(request, response);
    }
}
