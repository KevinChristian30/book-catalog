package com.learning.bookcatalog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@ActiveProfiles("default")
@Slf4j
public class PasswordEncryptedTest {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	public void encryptPassword() {
		log.info("password {}", passwordEncoder.encode("user"));
	}
}
