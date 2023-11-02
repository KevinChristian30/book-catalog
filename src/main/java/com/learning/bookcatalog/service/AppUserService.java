package com.learning.bookcatalog.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.learning.bookcatalog.dto.UserDetailResponseDTO;

public interface AppUserService extends UserDetailsService {
	public UserDetailResponseDTO findUserDetail();
}
