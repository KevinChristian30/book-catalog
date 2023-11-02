package com.learning.bookcatalog.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learning.bookcatalog.dto.PublisherCreateRequestDTO;
import com.learning.bookcatalog.dto.PublisherListResponseDTO;
import com.learning.bookcatalog.dto.PublisherUpdateRequestDTO;
import com.learning.bookcatalog.dto.ResultResponsePageDTO;
import com.learning.bookcatalog.service.PublisherService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@Validated
@RestController
@AllArgsConstructor
public class PublisherResource {
	private PublisherService publisherService;

	@PostMapping("/v1/publisher")
	public ResponseEntity<Void> createPublisher(
		@RequestBody 
		@Valid
		PublisherCreateRequestDTO dto
	) {
		publisherService.createPublisher(dto);
		
		return ResponseEntity.created(URI.create("/v1/publisher")).build();
	}
	
	@GetMapping("/v1/publisher/{id}")
	public ResponseEntity<PublisherUpdateRequestDTO> getPublisher(
		@PathVariable(name = "id")
		String id
	) {
		PublisherUpdateRequestDTO dto = publisherService.getPublisher(id);
		
		return ResponseEntity.ok(dto);
	}
	
	@PatchMapping("/v1/publisher/{id}")
	public ResponseEntity<Void> updatePublisher(
		@PathVariable(name = "id")
		@Size(max = 36, min = 36)
		String id,
		@RequestBody
		PublisherUpdateRequestDTO dto
	) {
		publisherService.updatePublisher(id, dto);
		
		return ResponseEntity.ok().build();
	}
	
//	@PreAuthorize("hasRole('READER')")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/v1/publisher")
	public ResponseEntity<ResultResponsePageDTO<PublisherListResponseDTO>> findPublisherList(
		@RequestParam(name = "pages", required = true, defaultValue = "0") Integer pages,
		@RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
		@RequestParam(name = "sortBy", required = true, defaultValue = "name") String sortBy,
		@RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
		@RequestParam(name = "publisherName", required = false) String publisherName
	) {
		return ResponseEntity.ok(publisherService.findPublisherList(pages, limit, sortBy, direction, publisherName));
	}
}
