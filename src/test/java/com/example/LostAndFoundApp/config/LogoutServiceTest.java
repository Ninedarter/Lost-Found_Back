package com.example.LostAndFoundApp.config;

import com.example.LostAndFoundApp.token.Token;
import com.example.LostAndFoundApp.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class LogoutServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private LogoutService logoutService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void logout_ValidToken_RevokesTokenAndClearsSecurityContext() {

        String validJwt = "validJwt";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + validJwt);
        when(tokenRepository.findByToken(validJwt)).thenReturn(Optional.ofNullable(mock(Token.class)));

        logoutService.logout(request, response, authentication);

        verify(tokenRepository, times(1)).findByToken(validJwt);
        verify(tokenRepository, times(1)).save(any(Token.class));
        verify(authentication, never()).getName();
    }

    @Test
    void logout_InvalidToken_DoesNotRevokeToken() {

        String invalidJwt = "invalidJwt";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + invalidJwt);
        when(tokenRepository.findByToken(invalidJwt)).thenReturn(Optional.empty());

        logoutService.logout(request, response, authentication);

        verify(tokenRepository, times(1)).findByToken(invalidJwt);
        verify(tokenRepository, never()).save(any(Token.class));
        verify(authentication, never()).getName();
    }

    @Test
    void logout_NoTokenHeader_DoesNothing() {

        when(request.getHeader("Authorization")).thenReturn(null);

        logoutService.logout(request, response, authentication);

        verify(tokenRepository, never()).findByToken(anyString());
        verify(tokenRepository, never()).save(any(Token.class));
        verify(authentication, never()).getName(); // Ensure SecurityContextHolder.clearContext() is not called
    }
}
