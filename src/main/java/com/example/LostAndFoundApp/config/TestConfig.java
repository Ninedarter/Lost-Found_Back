package com.example.LostAndFoundApp.config;

import com.example.LostAndFoundApp.auditing.ApplicationAuditAware;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

public class TestConfig {

    @Bean
    public AuditorAware<Integer> auditorAware() {
        return new ApplicationAuditAware();
    }
}
