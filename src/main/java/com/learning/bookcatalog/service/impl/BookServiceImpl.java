package com.learning.bookcatalog.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.learning.bookcatalog.domain.Author;
import com.learning.bookcatalog.domain.Book;
import com.learning.bookcatalog.domain.Category;
import com.learning.bookcatalog.domain.Publisher;
import com.learning.bookcatalog.dto.BookCreateRequestDTO;
import com.learning.bookcatalog.dto.BookDetailResponseDTO;
import com.learning.bookcatalog.dto.BookListResponseDTO;
import com.learning.bookcatalog.dto.BookQueryDTO;
import com.learning.bookcatalog.dto.BookUpdateRequestDTO;
import com.learning.bookcatalog.dto.ResultResponsePageDTO;
import com.learning.bookcatalog.exception.BadRequestException;
import com.learning.bookcatalog.repository.BookRepository;
import com.learning.bookcatalog.service.AuthorService;
import com.learning.bookcatalog.service.BookService;
import com.learning.bookcatalog.service.CategoryService;
import com.learning.bookcatalog.service.PublisherService;
import com.learning.bookcatalog.util.PaginationUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
	private final BookRepository bookRepository;

	private final AuthorService authorService;

	private final CategoryService categoryService;

	private final PublisherService publisherService;

	@Override
	public BookDetailResponseDTO findBookDetailById(String bookId) {
		Book book = bookRepository.findBySecureId(bookId).orElseThrow(() -> {
			return new BadRequestException("book_id.invalid");
		});

		BookDetailResponseDTO dto = new BookDetailResponseDTO();
		dto.setBookId(book.getSecureId());
		dto.setCategories(categoryService.constructDTO(book.getCategories()));
		dto.setPublisher(publisherService.constructDTO(book.getPublisher()));
		dto.setAuthors(authorService.constructDTO(book.getAuthors()));
		dto.setBookTitle(book.getTitle());
		dto.setBookDescription(book.getDescription());

		return dto;
	}

	@Override
	public List<BookDetailResponseDTO> findBookListDetail() {
		List<Book> books = bookRepository.findAll();

		return books.stream().map(book -> {
			BookDetailResponseDTO dto = new BookDetailResponseDTO();
//			dto.setBookId(book.getId());
//			dto.setAuthorName(book.getAuthor().getName());
			dto.setBookTitle(book.getTitle());
			dto.setBookDescription(book.getDescription());

			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public void createNewBook(BookCreateRequestDTO dto) {
		List<Author> authors = authorService.findAuthors(dto.getAuthorIdList());
		List<Category> categories = categoryService.findCategories(dto.getCategoryCodeList());
		Publisher publisher = publisherService.findPublisher(dto.getPublisherId());

		Book book = new Book();
		book.setAuthors(authors);
		book.setCategories(categories);
		book.setPublisher(publisher);
		book.setTitle(dto.getBookTitle());
		book.setDescription(dto.getDescription());
		book.setDeleted(false);

		bookRepository.save(book);
	}

	@Override
	public void updateBook(Long bookId, BookUpdateRequestDTO dto) {
		Book book = bookRepository.findById(bookId).orElseThrow(() -> {
			return new BadRequestException("book_id.invalid");
		});

		book.setTitle(dto.getBookTitle());
		book.setDescription(dto.getDescription());

		bookRepository.save(book);
	}

	@Override
	public void deleteBook(Long bookId) {
		bookRepository.deleteById(bookId);
	}

	@Override
	public ResultResponsePageDTO<BookListResponseDTO> findBookList(Integer page, Integer limit, String sortBy,
			String direction, String publisherName, String bookTitle, String authorName) {
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(sortBy), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);

		Page<BookQueryDTO> pageResult = bookRepository.findBookList(bookTitle, publisherName, authorName, pageable);
		List<Long> idList = pageResult.stream().map(b -> b.getId()).collect(Collectors.toList());
		Map<Long, List<String>> categoryMap = categoryService.findCategoriesMap(idList);
		Map<Long, List<String>> authorMap = authorService.findAuthorMap(idList);
		
		
		List<BookListResponseDTO> dtos = pageResult.stream().map(p -> {
			BookListResponseDTO dto = new BookListResponseDTO();

			dto.setAuthorNames(authorMap.get(p.getId()));
			dto.setCategoryCodes(categoryMap.get(p.getId()));
			dto.setTitle(p.getBookTitle());
			dto.setPublisherName(p.getPublisherName());
			dto.setDescription(p.getDescription());
			dto.setId(p.getSecureId());

			return dto;
		}).collect(Collectors.toList());

		return PaginationUtil.createResultPage(dtos, 
			pageResult.getTotalElements(), (long) pageResult.getTotalPages());
	}
}
