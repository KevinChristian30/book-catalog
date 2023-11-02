package com.learning.bookcatalog.security.util;

public interface TokenExtractor {
	public String extract(String payload);
}
