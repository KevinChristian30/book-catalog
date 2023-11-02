package com.learning.bookcatalog.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learning.bookcatalog.validator.annotation.ValidAuthorName;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuthorCreateRequestDTO {
	@NotBlank
	@ValidAuthorName
	private String authorName;
	
	@NotNull
	private Long dateOfBirth;
	
	private List<AddressCreateRequestDTO> addresses;
}
