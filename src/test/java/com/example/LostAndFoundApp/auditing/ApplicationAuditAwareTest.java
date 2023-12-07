package com.example.LostAndFoundApp.auditing;

import com.example.LostAndFoundApp.auditing.ApplicationAuditAware;
import com.example.LostAndFoundApp.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApplicationAuditAwareTest {

    private ApplicationAuditAware auditAware;

    @BeforeEach
    void setUp() {
        auditAware = new ApplicationAuditAware();
    }

    @Test
    @DisplayName("Get current auditor when authenticated")
    void testGetCurrentAuditorAuthenticated() {
        User user = new User();
        user.setId(1L);

        // Sample list of authorities
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));

        Authentication authentication = new TestingAuthenticationToken(user, null, authorities);
        setAuthentication(authentication);

        Optional<Integer> result = auditAware.getCurrentAuditor();

        Assertions.assertEquals(Optional.of(1), result);
    }

    @Test
    @DisplayName("Get current auditor when not authenticated")
    void testGetCurrentAuditorNotAuthenticated() {
        Authentication authentication = null;
        setAuthentication(authentication);

        Optional<Integer> result = auditAware.getCurrentAuditor();

        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("Get current auditor when anonymous authentication")
    void testGetCurrentAuditorAnonymousAuthentication() {
        User user = new User();
        user.setId(1L);

        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));

        Authentication authentication = new AnonymousAuthenticationToken("key", user, authorities);
        setAuthentication(authentication);

        Optional<Integer> result = auditAware.getCurrentAuditor();

        Assertions.assertEquals(Optional.empty(), result);
    }

    private void setAuthentication(Authentication authentication) {
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }
}
