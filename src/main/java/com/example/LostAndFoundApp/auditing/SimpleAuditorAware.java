package com.example.LostAndFoundApp.auditing;

import java.util.Optional;

public class SimpleAuditorAware implements org.springframework.data.domain.AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("testUser");
    }
}
