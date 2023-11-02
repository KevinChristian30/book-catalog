package com.learning.bookcatalog.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuthorUpdateRequestDTO {
	private String authorName;
	
	private Long birthDate;
	
	private List<AddressUpdateRequestDTO> addresses;
}
