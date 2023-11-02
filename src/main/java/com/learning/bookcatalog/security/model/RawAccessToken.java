package com.learning.bookcatalog.security.model;

import java.security.Key;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class RawAccessToken implements Token {
	private String token;

	public RawAccessToken(String token) {
		super();
		this.token = token;
	}

	@Override
	public String getToken() {
		return token;
	}

	public Jws<Claims> parseClaims(Key signingKey) {
		return Jwts.parser().setSigningKey(signingKey).build().parseClaimsJws(token);
	}
}
