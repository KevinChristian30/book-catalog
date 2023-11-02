package com.learning.bookcatalog.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddressUpdateRequestDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9202246957466036437L;
	
	private Long addressId;

	private String streetName;
	
	private String cityName;
	
	private String zipcode;
}
