package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtProviderConfig {

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider();
    }
}
