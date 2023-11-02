package com.learning.bookcatalog.service;

import java.util.List;
import java.util.Map;

import com.learning.bookcatalog.domain.Author;
import com.learning.bookcatalog.dto.AuthorCreateRequestDTO;
import com.learning.bookcatalog.dto.AuthorResponseDTO;
import com.learning.bookcatalog.dto.AuthorUpdateRequestDTO;

public interface AuthorService {
	public AuthorResponseDTO findAuthorById(String id);
	
	public void createNewAuthor(List<AuthorCreateRequestDTO> dtos);
	
	public void updateAuthor(String authorId, AuthorUpdateRequestDTO dto);
	
	public void deleteAuthor(String authorId);
	
	public List<Author> findAuthors(List<String> authorIdList);
	
	public List<AuthorResponseDTO> constructDTO(List<Author> authors);

	public Map<Long, List<String>> findAuthorMap(List<Long> authorIdList);
}
