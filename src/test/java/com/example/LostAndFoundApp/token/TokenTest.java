package com.example.LostAndFoundApp.token;

import com.example.LostAndFoundApp.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TokenTest {

    private Token token;
    private User user;

    @BeforeEach
    void setUp() {

        user = new User();
        token = Token.builder()
                .id(1)
                .token("testToken")
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .user(user)
                .build();
    }

    @Test
    void testGettersAndSetters() {

        assertEquals(1, token.getId());
        assertEquals("testToken", token.getToken());
        assertEquals(TokenType.BEARER, token.getTokenType());
        assertFalse(token.isRevoked());
        assertFalse(token.isExpired());


        token.setId(2);
        token.setToken("newTestToken");
        token.setTokenType(TokenType.BEARER);
        token.setRevoked(true);
        token.setExpired(true);
        token.setUser(null);

        assertEquals(2, token.getId());
        assertEquals("newTestToken", token.getToken());
        assertEquals(TokenType.BEARER, token.getTokenType());
        assertTrue(token.isRevoked());
        assertTrue(token.isExpired());
        assertNull(token.getUser());
    }

    @Test
    void testBuilder() {

        Token newToken = Token.builder()
                .id(3)
                .token("anotherToken")
                .tokenType(TokenType.BEARER)
                .revoked(true)
                .expired(true)
                .user(user)
                .build();

        assertEquals(3, newToken.getId());
        assertEquals("anotherToken", newToken.getToken());
        assertEquals(TokenType.BEARER, newToken.getTokenType());
        assertTrue(newToken.isRevoked());
        assertTrue(newToken.isExpired());
        assertEquals(user, newToken.getUser());
    }



}
