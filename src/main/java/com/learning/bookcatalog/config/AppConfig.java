package com.learning.bookcatalog.config;

import java.security.Key;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.bookcatalog.security.util.JWTTokenFactory;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Configuration
public class AppConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
    @Bean
    ObjectMapper objectMapper() {
    	return new ObjectMapper();
    }
    
    @Bean
    Key key() {
    	byte[] keyBytes = Decoders.BASE64.decode("alsjfhaskldfjhalskfdjhlaskdfj");
    	return Keys.hmacShaKeyFor(keyBytes);
    }
    
    @Bean
    JWTTokenFactory jwtTokenFactory(Key key) {
    	return new JWTTokenFactory(key);
    }
}
