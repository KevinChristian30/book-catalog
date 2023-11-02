package com.learning.bookcatalog.util;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.learning.bookcatalog.dto.ResultResponsePageDTO;

public class PaginationUtil {
	public static <T> ResultResponsePageDTO<T> createResultPage(List<T> dtos, Long totalElements, Long pages) {
		ResultResponsePageDTO<T> result = new ResultResponsePageDTO<>();
		
		result.setPages(pages);
		result.setElements(totalElements);
		result.setResult(dtos);
		
		return result;
	}
	
	public static Sort.Direction getSortBy(String sortBy) {
		if (sortBy.equalsIgnoreCase("asc")) {
			return Sort.Direction.ASC;
		} else {
			return Sort.Direction.DESC;
		}
	}
}
