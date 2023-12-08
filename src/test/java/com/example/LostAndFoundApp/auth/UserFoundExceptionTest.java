package com.example.LostAndFoundApp.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserFoundExceptionTest {

    @Test
    public void testExceptionMessage() {
        String message = "User already found";
        UserFoundException exception = new UserFoundException(message);

        Assertions.assertEquals(message, exception.getMessage());
    }

}
