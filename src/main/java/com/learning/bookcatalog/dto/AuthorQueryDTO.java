package com.learning.bookcatalog.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorQueryDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5487745807404330122L;

	private Long bookId;
	
	private String authorName;
}
