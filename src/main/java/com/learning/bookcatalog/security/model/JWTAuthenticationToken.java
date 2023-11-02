package com.learning.bookcatalog.security.model;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JWTAuthenticationToken extends AbstractAuthenticationToken {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5019427596139537736L;

	private RawAccessToken rawAccessToken;
	
	private UserDetails userDetails;
	
	public JWTAuthenticationToken(RawAccessToken token) {
		super(null);
		this.rawAccessToken = token;
		super.setAuthenticated(false);
	}
	
	public JWTAuthenticationToken(
		UserDetails userDetails,
		Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.eraseCredentials();
		this.userDetails = userDetails;
		this.setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return this.rawAccessToken;
	}

	@Override
	public Object getPrincipal() {
		return this.userDetails;
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		this.rawAccessToken = null;
	}
}
