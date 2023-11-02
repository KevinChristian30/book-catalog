package com.learning.bookcatalog.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResultResponsePageDTO<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8645543987518761390L;

	private List<T> result;

	private Long pages;
	
	private Long elements;
}
