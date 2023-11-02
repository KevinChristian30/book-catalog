package com.learning.bookcatalog.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.bookcatalog.dto.HelloMessageResponseDTO;
import com.learning.bookcatalog.service.GreetingService;

@RestController
public class HelloResource {
    private GreetingService greetingService;
    
    public HelloResource(GreetingService greetingService) {
        super();
        this.greetingService = greetingService;
    }

    @GetMapping("/hello")
    public ResponseEntity<HelloMessageResponseDTO> helloWorld() {
    	HelloMessageResponseDTO helloMessageResponseDTO = new HelloMessageResponseDTO();
    	helloMessageResponseDTO.setMessage(greetingService.sayGreeting());
    	
    	return ResponseEntity
				.accepted()
				.body(helloMessageResponseDTO);
    }
}
