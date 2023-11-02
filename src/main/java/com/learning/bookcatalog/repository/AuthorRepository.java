package com.learning.bookcatalog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.learning.bookcatalog.domain.Author;
import com.learning.bookcatalog.dto.AuthorQueryDTO;

public interface AuthorRepository extends JpaRepository<Author, Long>{
	public Optional<Author> findByIdAndDeletedFalse(Long id);
	
	public Optional<Author> findBySecureId(String secureId);
	
	public List<Author> findBySecureIdIn(List<String> authorIdList);

	@Query("SELECT new com.learning.bookcatalog.dto.AuthorQueryDTO(b.id, ba.name) "
			+ "FROM Book b "
			+ "JOIN b.authors ba "
			+ "WHERE b.id IN :bookIdList")
	public List<AuthorQueryDTO> findAuthorsByBookIdList(List<Long> bookIdList);
}
