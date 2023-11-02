package com.learning.bookcatalog.security.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.learning.bookcatalog.security.model.AnonymousAuthentication;
import com.learning.bookcatalog.security.model.JWTAuthenticationToken;
import com.learning.bookcatalog.security.model.RawAccessToken;
import com.learning.bookcatalog.security.util.TokenExtractor;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthProcessingFilter extends AbstractAuthenticationProcessingFilter {
	private final TokenExtractor tokenExtractor;
	
	private final AuthenticationFailureHandler failureHandler;
	
	public JWTAuthProcessingFilter(
		RequestMatcher requiresAuthenticationRequestMatcher, 
		TokenExtractor tokenExtractor,
		AuthenticationFailureHandler failureHandler
	) {
	
		super(requiresAuthenticationRequestMatcher);
		this.tokenExtractor = tokenExtractor;
		this.failureHandler = failureHandler;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		String authorizationHeader = request.getHeader("Auhtorization");
		String jwt = tokenExtractor.extract(authorizationHeader);
		
		RawAccessToken rawToken = new RawAccessToken(jwt);
		
		return this
			.getAuthenticationManager()
			.authenticate(new JWTAuthenticationToken(rawToken));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authResult);
		SecurityContextHolder.setContext(context);
		chain.doFilter(request, response);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		SecurityContextHolder.clearContext();
		
		SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthentication());
	}
}
