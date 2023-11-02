package com.learning.bookcatalog.security.model;

import io.jsonwebtoken.Claims;

public class AccessJWTToken implements Token {
	private final String rawToken;
	
	private Claims claims;
	
	public AccessJWTToken(String rawToken, Claims claims) {
		super();
		this.rawToken = rawToken;
		this.claims = claims;
	}

	@Override
	public String getToken() {
		return rawToken;
	}
}
