package com.example.LostAndFoundApp.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    private final User user = User.builder()
            .id(1L)
            .firstname("John")
            .lastname("Doe")
            .email("john.doe@example.com")
            .phoneNumber("123456789")
            .dob(LocalDate.of(1990, 1, 1))
            .gender(Gender.MALE)
            .password("password")
            .role(Role.USER)
            .tokens(Collections.emptyList())
            .build();

    @Test
    void getAuthorities() {
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        List<SimpleGrantedAuthority> expectedAuthorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_USER")
        );

        Assertions.assertEquals(expectedAuthorities, authorities);
    }

    @Test
    void getPassword() {
        Assertions.assertEquals("password", user.getPassword());
    }

    @Test
    void getUsername() {
        Assertions.assertEquals("john.doe@example.com", user.getUsername());
    }

    @Test
    void isAccountNonExpired() {
        Assertions.assertTrue(user.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked() {
        Assertions.assertTrue(user.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired() {
        Assertions.assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    void isEnabled() {
        Assertions.assertTrue(user.isEnabled());
    }

    @Test
    void getLastname() {
        Assertions.assertEquals("Doe", user.getLastname());
    }

    @Test
    void getDob() {
        Assertions.assertEquals(LocalDate.of(1990, 1, 1), user.getDob());
    }

    @Test
    void getGender() {
        Assertions.assertEquals(Gender.MALE, user.getGender());
    }

    @Test
    void getRole() {
        Assertions.assertEquals(Role.USER, user.getRole());
    }

    @Test
    void getTokens() {
        Assertions.assertEquals(Collections.emptyList(), user.getTokens());
    }


}
