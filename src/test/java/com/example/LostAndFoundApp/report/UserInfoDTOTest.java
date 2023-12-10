package com.example.LostAndFoundApp.report;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserInfoDTOTest {

    @Test
    @DisplayName("Test getters and setters")
    public void testGettersAndSetters() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setEmail("test@example.com");
        userInfoDTO.setUsername("testuser");
        userInfoDTO.setAccountNonLocked(true);

        String email = userInfoDTO.getEmail();
        String username = userInfoDTO.getUsername();
        boolean accountNonLocked = userInfoDTO.isAccountNonLocked();

        Assertions.assertEquals("test@example.com", email);
        Assertions.assertEquals("testuser", username);
        Assertions.assertTrue(accountNonLocked);
    }

}
