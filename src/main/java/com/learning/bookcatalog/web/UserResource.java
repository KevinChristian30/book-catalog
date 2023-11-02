package com.learning.bookcatalog.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.bookcatalog.dto.UserDetailResponseDTO;
import com.learning.bookcatalog.service.AppUserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserResource {
	private AppUserService appUserService;
	
	@GetMapping("/v1/user")
	public ResponseEntity<UserDetailResponseDTO> findUserDetail() {
		return ResponseEntity.ok(appUserService.findUserDetail());
	}
}
