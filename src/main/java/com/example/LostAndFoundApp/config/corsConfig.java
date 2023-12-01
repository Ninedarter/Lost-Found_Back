package com.example.LostAndFoundApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class corsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Apply CORS for specific paths
                .allowedOrigins("http://localhost:3000") // Specify allowed origins
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") // Specify allowed methods
                .allowedHeaders("*"); // Specify allowed headers
    }
}
