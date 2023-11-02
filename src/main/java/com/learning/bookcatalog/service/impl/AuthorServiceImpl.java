package com.learning.bookcatalog.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.learning.bookcatalog.domain.Address;
import com.learning.bookcatalog.domain.Author;
import com.learning.bookcatalog.dto.AuthorCreateRequestDTO;
import com.learning.bookcatalog.dto.AuthorQueryDTO;
import com.learning.bookcatalog.dto.AuthorResponseDTO;
import com.learning.bookcatalog.dto.AuthorUpdateRequestDTO;
import com.learning.bookcatalog.exception.BadRequestException;
import com.learning.bookcatalog.exception.ResourceNotFoundException;
import com.learning.bookcatalog.repository.AuthorRepository;
import com.learning.bookcatalog.service.AuthorService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
	private AuthorRepository authorRepository;

	@Override
	public AuthorResponseDTO findAuthorById(String id) {
		Author author = authorRepository.findBySecureId(id)
				.orElseThrow(() -> new ResourceNotFoundException("invalid.authorid"));

		AuthorResponseDTO authorResponseDTO = new AuthorResponseDTO();
		authorResponseDTO.setAuthorName(author.getName());
		authorResponseDTO.setBirthDate(author.getDateofBirth().toEpochDay());

		return authorResponseDTO;
	}

	@Override
	public void createNewAuthor(List<AuthorCreateRequestDTO> dtos) {
		List<Author> authors = dtos.stream().map(dto -> {
			Author author = new Author();

			author.setName(dto.getAuthorName());
			author.setDateofBirth(LocalDate.ofEpochDay(dto.getDateOfBirth()));
			author.setDeleted(false);
			
			List<Address> addresses = dto.getAddresses().stream().map(a -> {
				Address address = new Address();
				
				address.setCityName(a.getCityName());
				address.setStreetName(a.getStreetName());
				address.setZipCode(a.getZipcode());
				address.setAuthor(author);
				
				return address;
			}).collect(Collectors.toList());
			author.setAddresses(addresses);

			return author;
		}).collect(Collectors.toList());

		authorRepository.saveAll(authors);
	}

	@Override
	public void updateAuthor(String authorId, AuthorUpdateRequestDTO dto) {
		Author author = authorRepository.findBySecureId(authorId)
				.orElseThrow(() -> new BadRequestException("invalid.authorid"));
		
		Map<Long, Address> addressMap = author.getAddresses().stream().map(t -> {
			return t;
		}).collect(Collectors.toMap(Address::getId, Function.identity()));
		
		List<Address> addresses = dto.getAddresses().stream().map(a -> {
			Address address = addressMap.get(a.getAddressId());
			
			address.setStreetName(a.getStreetName());
			address.setCityName(a.getCityName());
			address.setZipCode(a.getZipcode());
			
			return address;
		}).collect(Collectors.toList());
		
		
		author.setName(dto.getAuthorName() == null ? author.getName() : dto.getAuthorName());
		author.setDateofBirth(
				dto.getBirthDate() == null ? author.getDateofBirth() : LocalDate.ofEpochDay(dto.getBirthDate()));
		author.setAddresses(addresses);
		
		authorRepository.save(author);
	}

	@Override
	public void deleteAuthor(String authorId) {
		Author author = authorRepository.findBySecureId(authorId)
				.orElseThrow(() -> new BadRequestException("invalid.authorid"));

		authorRepository.delete(author);
	}

	@Override
	public List<Author> findAuthors(List<String> authorIdList) {
		List<Author> authors = authorRepository.findBySecureIdIn(authorIdList);
		if (authors.isEmpty()) {
			throw new BadRequestException("author is empty");
		}

		return authors;
	}

	@Override
	public List<AuthorResponseDTO> constructDTO(List<Author> authors) {
		return authors.stream().map(e -> {
			AuthorResponseDTO dto = new AuthorResponseDTO();
			
			dto.setAuthorName(e.getName());
			dto.setBirthDate(e.getDateofBirth().toEpochDay());
			
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public Map<Long, List<String>> findAuthorMap(List<Long> bookIdList) {
		List<AuthorQueryDTO> queryList = authorRepository.findAuthorsByBookIdList(bookIdList);
		Map<Long, List<String>> authorMap = new HashMap<>();
		List<String> authorList = null;
		
		for (AuthorQueryDTO dto : queryList) {
			if (!authorMap.containsKey(dto.getBookId())) {
				authorList = new ArrayList<>();
			} else {
				authorList = authorMap.get(dto.getBookId());
			}
			
			authorList.add(dto.getAuthorName());
			authorMap.put(dto.getBookId(), authorList);
		}
		
		return authorMap;
	}
}
