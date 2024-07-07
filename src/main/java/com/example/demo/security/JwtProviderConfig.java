package com.example.demo.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtProviderConfig {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expiration}")
    private long validityInMilliseconds;

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider(secretKey, validityInMilliseconds);
    }
}
