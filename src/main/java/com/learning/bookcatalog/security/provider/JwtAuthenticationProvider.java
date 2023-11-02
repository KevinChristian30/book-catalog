package com.learning.bookcatalog.security.provider;

import java.security.Key;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.learning.bookcatalog.security.model.JWTAuthenticationToken;
import com.learning.bookcatalog.security.model.RawAccessToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
	private final Key key;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		RawAccessToken token = (RawAccessToken) authentication.getCredentials();
		Jws<Claims> jwsClaims = token.parseClaims(key);
		
		String subject = jwsClaims.getBody().getSubject();
		List<String> scopes = jwsClaims.getBody().get("scope", List.class);
		
		List<GrantedAuthority> authorities = 
			scopes
				.stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		
		UserDetails userDetails = new UserDetails() {
			
			@Override
			public boolean isEnabled() {
				return true;
			}
			
			@Override
			public boolean isCredentialsNonExpired() {
				return true;
			}
			
			@Override
			public boolean isAccountNonLocked() {
				return true;
			}
			
			@Override
			public boolean isAccountNonExpired() {
				return true;
			}
			
			@Override
			public String getUsername() {
				return subject;
			}
			
			@Override
			public String getPassword() {
				return null;
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return authorities;
			}
		}; 
		
		return new JWTAuthenticationToken(userDetails, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JWTAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
