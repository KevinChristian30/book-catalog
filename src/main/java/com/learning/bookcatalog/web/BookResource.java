package com.learning.bookcatalog.web;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learning.bookcatalog.dto.BookCreateRequestDTO;
import com.learning.bookcatalog.dto.BookDetailResponseDTO;
import com.learning.bookcatalog.dto.BookListResponseDTO;
import com.learning.bookcatalog.dto.BookUpdateRequestDTO;
import com.learning.bookcatalog.dto.ResultResponsePageDTO;
import com.learning.bookcatalog.service.BookService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BookResource {
	private final BookService bookService;
	
	@GetMapping("/v1/books/{bookId}")
	public ResponseEntity<BookDetailResponseDTO> findBookDetail(@PathVariable("bookId") String id) {
		return ResponseEntity.ok(bookService.findBookDetailById(id));
	}
	
	@GetMapping("/v2/books")
	public ResponseEntity<ResultResponsePageDTO<BookListResponseDTO>> findBookListV2(
		@RequestParam(name = "page", required = true, defaultValue = "0") Integer page,
		@RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
		@RequestParam(name = "sortBy", required = true, defaultValue = "title") String sortBy,
		@RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
		@RequestParam(name = "bookTitle", required = false, defaultValue = "") String bookTitle,
		@RequestParam(name = "publisherName", required = false, defaultValue = "") String publisherName,
		@RequestParam(name = "authorName", required = false, defaultValue = "") String authorName) {
		return ResponseEntity.ok(bookService.findBookList(page, limit, sortBy, direction, publisherName, bookTitle, authorName));
	}
	
	@PostMapping("/v1/books")
	public ResponseEntity<Void> createNewBook(
		@RequestBody 
		@Valid
		BookCreateRequestDTO bookCreateDTO
	) {
		bookService.createNewBook(bookCreateDTO);
		
		return ResponseEntity.created(URI.create("/books")).build();
	}
	
	@GetMapping("/v1/books")
	public ResponseEntity<List<BookDetailResponseDTO>> findBookList() {
		return ResponseEntity
				.ok()
				.body(bookService.findBookListDetail());
	}
	
	@PutMapping("/v1/books/{bookId}")
	public ResponseEntity<Void> updateBook(
		@PathVariable("bookId")
		Long bookId,
		@RequestBody
		@Valid
		BookUpdateRequestDTO dto
	) {
		bookService.updateBook(bookId, dto);
		
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/v1/books/{bookId}")
	public ResponseEntity<Void> deleteBook(
		@PathVariable("bookId") Long bookId
	) {
		bookService.deleteBook(bookId);
		
		return ResponseEntity.ok().build();
	}
}
