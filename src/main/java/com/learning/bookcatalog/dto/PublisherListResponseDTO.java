package com.learning.bookcatalog.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PublisherListResponseDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8642560267282900653L;

	private String publisherName;
	
	private String publisherId;
	
	private String companyName;
}
