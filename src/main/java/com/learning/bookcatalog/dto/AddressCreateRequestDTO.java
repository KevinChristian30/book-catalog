package com.learning.bookcatalog.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddressCreateRequestDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4335442960491928781L;

	private String streetName;
	
	private String cityName;
	
	private String zipcode;
}
