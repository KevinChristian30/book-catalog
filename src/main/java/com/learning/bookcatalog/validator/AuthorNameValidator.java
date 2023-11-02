package com.learning.bookcatalog.validator;

import org.springframework.stereotype.Component;

import com.learning.bookcatalog.validator.annotation.ValidAuthorName;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class AuthorNameValidator implements ConstraintValidator<ValidAuthorName, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return !value.equals("kevin");
	}
	
}
