package com.learning.bookcatalog.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.learning.bookcatalog.enums.ErrorCode;

import lombok.Data;

@Data
public class ErrorResponseDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6770791751045858105L;
	
	private Date timestamp;
	
	private String message;
	
	private ErrorCode errorCode;
	
	private List<String> details;
	
	private HttpStatus status;
	
	public static ErrorResponseDTO of(
		final String message, 
		List<String> details, 
		final ErrorCode errorCode, 
		HttpStatus httpStatus) {
		return new ErrorResponseDTO(message, errorCode, details, httpStatus);
	}

	public ErrorResponseDTO(String message, ErrorCode errorCode, List<String> details,
			HttpStatus status) {
		super();
		this.timestamp = new Date();
		this.message = message;
		this.errorCode = errorCode;
		this.details = details;
		this.status = status;	
	}
	
	
}
