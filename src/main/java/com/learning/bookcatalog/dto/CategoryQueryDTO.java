package com.learning.bookcatalog.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryQueryDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7796463965728009203L;

	private Long bookId;
	
	private String categoryCode;
}
