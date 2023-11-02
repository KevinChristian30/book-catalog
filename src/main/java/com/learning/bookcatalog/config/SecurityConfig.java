package com.learning.bookcatalog.config;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.bookcatalog.security.filter.JWTAuthProcessingFilter;
import com.learning.bookcatalog.security.filter.UsernamePasswordAuthProcessingFilter;
import com.learning.bookcatalog.security.handler.UsernamePasswordAuthFailureHandler;
import com.learning.bookcatalog.security.handler.UsernamePasswordAuthSuccessHandler;
import com.learning.bookcatalog.security.provider.JwtAuthenticationProvider;
import com.learning.bookcatalog.security.provider.UsernamePasswordAuthProvider;
import com.learning.bookcatalog.security.util.JWTTokenFactory;
import com.learning.bookcatalog.security.util.SkipPathRequestMatcher;
import com.learning.bookcatalog.security.util.TokenExtractor;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
	private static final String AUTH_URL = "/v1/login";
	private static final String V1_URL = "/v1/*";
	private static final String V2_URL = "/v2/*";
	
	private final static List<String> PERMIT_ENDPOINT_LIST = 
			Arrays.asList(AUTH_URL);
		private final static List<String> AUTHENTICATED_ENDPOINT_LIST = 
			Arrays.asList(V1_URL, V2_URL);
	
	@Autowired
	private UsernamePasswordAuthProvider usernamePasswordAuthProvider;
	
	@Autowired
	private JwtAuthenticationProvider authenticationProvider;
	
	@Bean
	public AuthenticationSuccessHandler successHandler(
		ObjectMapper objectMapper,
		JWTTokenFactory jwtTokenFactory
	) {
		return new UsernamePasswordAuthSuccessHandler(objectMapper, jwtTokenFactory);
	}
	
	@Bean
	public AuthenticationFailureHandler failureHandler(ObjectMapper objectMapper) {
		return new UsernamePasswordAuthFailureHandler(objectMapper);
	}
	
	@Bean
	AuthenticationManager authenticationManager(
		AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public UsernamePasswordAuthProcessingFilter usernamePasswordAuthProcessingFilter(
		ObjectMapper objectMapper,
		AuthenticationSuccessHandler successHandler,
		AuthenticationFailureHandler failureHandler,
		AuthenticationManager authenticationManager
	) {
		UsernamePasswordAuthProcessingFilter filter = 
			new UsernamePasswordAuthProcessingFilter(
				AUTH_URL, objectMapper, successHandler, failureHandler);
		filter.setAuthenticationManager(authenticationManager);
		return filter;
	}
	
	@Bean
	public JWTAuthProcessingFilter jwtAuthProcessingFilter(
		TokenExtractor extractor,
		AuthenticationFailureHandler authenticationFailureHandler,
		AuthenticationManager authenticationManager
	) {
		SkipPathRequestMatcher matcher = 
				new SkipPathRequestMatcher(
					PERMIT_ENDPOINT_LIST, AUTHENTICATED_ENDPOINT_LIST);
		
		JWTAuthProcessingFilter filter = 
			new JWTAuthProcessingFilter(
				matcher, extractor, authenticationFailureHandler);
		
		filter.setAuthenticationManager(authenticationManager);
		
		return filter;
	}
	
	@Autowired
	void registerProvider(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.authenticationProvider(usernamePasswordAuthProvider)
		.authenticationProvider(authenticationProvider);
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(
		HttpSecurity http, 
		UsernamePasswordAuthProcessingFilter usernamePasswordAuthProcessingFilter,
		JWTAuthProcessingFilter jwtAuthProcessingFilter
	) throws Exception {
		http.authorizeHttpRequests().requestMatchers(V1_URL, V1_URL).authenticated()
		.and()
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.httpBasic();
		
//		http.addFilterBefore(
//			usernamePasswordAuthProcessingFilter, 
//			UsernamePasswordAuthProcessingFilter.class);
		
		http.addFilterBefore(jwtAuthProcessingFilter, JWTAuthProcessingFilter.class);
		
		return http.build();
	}
}
