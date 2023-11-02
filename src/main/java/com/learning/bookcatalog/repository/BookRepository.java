package com.learning.bookcatalog.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.learning.bookcatalog.domain.Book;
import com.learning.bookcatalog.dto.BookQueryDTO;

public interface BookRepository extends JpaRepository<Book, Long>{
	// SQL: SELECT b FROM book b WHERE b.secure_id = :id;	
	// JPQL: SELECT b FROM Book b WHERE b.secureId = :id;	
	public Optional<Book> findBySecureId(String secureId);
	
	@Query("SELECT DISTINCT new com.learning.bookcatalog.dto.BookQueryDTO(b.id, b.secureId, b.title, p.name, b.description) "
			+ "FROM Book b "
			+ "INNER JOIN Publisher p "
			+ "ON b.publisher.id = p.id "
			+ "JOIN b.authors ba "
			+ "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :publisherName , '%')) "
			+ "AND "
			+ "LOWER(b.title) LIKE LOWER(CONCAT('%', :bookTitle , '%')) "
			+ "AND "
			+ "LOWER(ba.name) LIKE LOWER(CONCAT('%', :authorName, '%'))")
	public Page<BookQueryDTO> findBookList(String bookTitle, String publisherName, String authorName, Pageable pageable);
}
