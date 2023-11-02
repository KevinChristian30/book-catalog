package com.learning.bookcatalog.service.impl;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learning.bookcatalog.dto.UserDetailResponseDTO;
import com.learning.bookcatalog.exception.BadRequestException;
import com.learning.bookcatalog.repository.AppUserRepository;
import com.learning.bookcatalog.service.AppUserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AppUserServiceImpl implements AppUserService {
	private final AppUserRepository appUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return appUserRepository
			.findByUsername(username)
			.orElseThrow(() -> {
				return new BadRequestException("Invalid Credentials");
			});
	}

	@Override
	public UserDetailResponseDTO findUserDetail() {
		SecurityContext ctx = SecurityContextHolder.getContext();
		
		UserDetailResponseDTO dto = new UserDetailResponseDTO();
		dto.setUsername(ctx.getAuthentication().getName());
		
		return dto;
	}
}




