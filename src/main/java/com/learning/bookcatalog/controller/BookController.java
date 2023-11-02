package com.learning.bookcatalog.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.learning.bookcatalog.dto.BookCreateRequestDTO;
import com.learning.bookcatalog.dto.BookDetailResponseDTO;
import com.learning.bookcatalog.service.BookService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping("/books")
public class BookController {
	private final BookService bookService;
	
	@GetMapping("/list")
	public String list(Model model) {
		List<BookDetailResponseDTO> books = bookService.findBookListDetail();
		model.addAttribute("books", books);
		return "book/list";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		BookCreateRequestDTO bookCreateDTO = new BookCreateRequestDTO();
		model.addAttribute("bookCreateDTO", bookCreateDTO);
		
		return "book/create";
	}
	
	@PostMapping("/create")
	public String store(
		@ModelAttribute("bookCreateDTO") 
		@Valid 
		BookCreateRequestDTO dto, 
		BindingResult bindingResult,
		Errors errors,
		Model model
	) {
		if (errors.hasErrors()) {
			model.addAttribute("bookCreateDTO", dto);
			return "book/create";
		}
		
		bookService.createNewBook(dto);
		
		return "redirect:/books/list";
	}
}
