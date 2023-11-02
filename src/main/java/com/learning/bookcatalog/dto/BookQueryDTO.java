package com.learning.bookcatalog.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookQueryDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4082470661896849746L;

	private Long id;
	
	private String secureId;
	
	private String bookTitle;
	
	private String publisherName;
	
	private String description;
}
