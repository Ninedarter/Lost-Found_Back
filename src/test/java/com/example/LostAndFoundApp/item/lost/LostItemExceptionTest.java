package com.example.LostAndFoundApp.item.lost;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LostItemExceptionTest {

    @Test
    void defaultConstructor_MessageIsNull() {
        LostItemException exception = new LostItemException();

        Assertions.assertNull(exception.getMessage());
    }

    @Test
    void parameterizedConstructor_MessageIsSet() {
        String errorMessage = "Custom error message";
        LostItemException exception = new LostItemException(errorMessage);

        Assertions.assertEquals(errorMessage, exception.getMessage());
    }
}
