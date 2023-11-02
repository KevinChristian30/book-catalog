package com.learning.bookcatalog.security.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

@Component
public class JWtHeaderTokenExtractor implements TokenExtractor {
	private static final String HEADER_PREFIX = "Bearer ";
	
	@Override
	public String extract(String payload) {
		if (StringUtils.isBlank(payload)) {
			throw new AuthenticationServiceException("Header should be provided");
		}
		
		if (payload.length() < HEADER_PREFIX.length()) {
			throw new AuthenticationServiceException("Invalid authorization header");
		}
		
		return payload.substring(HEADER_PREFIX.length(), payload.length()); 
	}
}
