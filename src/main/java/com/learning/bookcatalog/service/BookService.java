package com.learning.bookcatalog.service;

import java.util.List;

import com.learning.bookcatalog.dto.BookCreateRequestDTO;
import com.learning.bookcatalog.dto.BookDetailResponseDTO;
import com.learning.bookcatalog.dto.BookListResponseDTO;
import com.learning.bookcatalog.dto.BookUpdateRequestDTO;
import com.learning.bookcatalog.dto.ResultResponsePageDTO;

public interface BookService {
	public BookDetailResponseDTO findBookDetailById(String bookId);
	
	public List<BookDetailResponseDTO> findBookListDetail();
	
	public void createNewBook(BookCreateRequestDTO dto);
	
	public void updateBook(Long bookId, BookUpdateRequestDTO dto);
	
	public void deleteBook(Long bookId);
	
	public ResultResponsePageDTO<BookListResponseDTO> findBookList(Integer page, Integer limit, String sortBy, String direction, String publisherName, String authorName, String bookTitle);
}
