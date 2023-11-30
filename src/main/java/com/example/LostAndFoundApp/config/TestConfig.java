package com.example.LostAndFoundApp.config;

import com.example.LostAndFoundApp.auditing.SimpleAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

public class TestConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new SimpleAuditorAware();
    }
}
