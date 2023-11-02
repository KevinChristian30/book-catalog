package com.learning.bookcatalog.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PublisherUpdateRequestDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2138099252356319533L;

	private String publisherName;

	private String companyName;
	
	private String address;
}
