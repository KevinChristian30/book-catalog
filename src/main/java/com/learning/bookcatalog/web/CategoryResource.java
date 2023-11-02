package com.learning.bookcatalog.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learning.bookcatalog.dto.CategoryCreateAndUpdateRequestDTO;
import com.learning.bookcatalog.dto.CategoryListResponseDTO;
import com.learning.bookcatalog.dto.ResultResponsePageDTO;
import com.learning.bookcatalog.service.CategoryService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CategoryResource {
	private final CategoryService categoryService;
	
	@PostMapping("/v1/category")
	public ResponseEntity<Void> createAndUpdateCategory(
		@RequestBody
		@Valid
		CategoryCreateAndUpdateRequestDTO dto
	) {
		categoryService.createAndUpdateCategory(dto);
		return ResponseEntity.created(URI.create("/v1/category")).build();
	}
	
	@GetMapping("/v1/category")
	public ResponseEntity<ResultResponsePageDTO<CategoryListResponseDTO>> findCategoryList(
		@RequestParam(name = "pages", required = true, defaultValue = "0") Integer pages,
		@RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
		@RequestParam(name = "sortBy", required = true, defaultValue = "name") String sortBy,
		@RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
		@RequestParam(name = "categoryName", required = false) String categoryName
	) {
		return ResponseEntity.ok(categoryService.findCategoryList(pages, limit, sortBy, direction, categoryName));
	}
}
