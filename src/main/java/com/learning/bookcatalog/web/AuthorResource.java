package com.learning.bookcatalog.web;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.learning.bookcatalog.dto.AuthorCreateRequestDTO;
import com.learning.bookcatalog.dto.AuthorResponseDTO;
import com.learning.bookcatalog.dto.AuthorUpdateRequestDTO;
import com.learning.bookcatalog.service.AuthorService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@Validated
public class AuthorResource {
	private final AuthorService authorService;
	
	@GetMapping("/v1/author/{id}/detail")
	public ResponseEntity<AuthorResponseDTO> findAuthorById(
		@PathVariable(name = "id") String id
	) {
		return ResponseEntity.ok(authorService.findAuthorById(id));
	}
	
	@PostMapping("/v1/author")
	public ResponseEntity<Void> createNewAuthor(
		@RequestBody 
		List<AuthorCreateRequestDTO> dto
	) {
		authorService.createNewAuthor(dto);
		return ResponseEntity.created(URI.create("/author")).build();
	}
	
	@PatchMapping("/v1/author/{id}")
	public ResponseEntity<Void> updateAuthor(
		@PathVariable(name = "id")
		String id,
		@RequestBody
		AuthorUpdateRequestDTO dto
	) {
		authorService.updateAuthor(id, dto);
		
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/v1/author/{id}")
	public ResponseEntity<Void> deleteAuthor(
		@PathVariable(name = "id") String id
	) {
		authorService.deleteAuthor(id);
		
		return ResponseEntity.ok().build();
	}
}
