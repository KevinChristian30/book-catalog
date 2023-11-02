package com.learning.bookcatalog.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PublisherCreateRequestDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2706937920448592760L;

	@NotBlank(message = "publisher must not be blank")
	private String publisherName;

	@NotBlank(message = "company name must not be blank")
	private String companyName;
	
	private String address;
}
