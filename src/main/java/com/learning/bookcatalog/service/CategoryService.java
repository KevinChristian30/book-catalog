package com.learning.bookcatalog.service;

import java.util.List;
import java.util.Map;

import com.learning.bookcatalog.domain.Category;
import com.learning.bookcatalog.dto.CategoryCreateAndUpdateRequestDTO;
import com.learning.bookcatalog.dto.CategoryListResponseDTO;
import com.learning.bookcatalog.dto.ResultResponsePageDTO;

public interface CategoryService {
	public void createAndUpdateCategory(CategoryCreateAndUpdateRequestDTO dto);
	
	public ResultResponsePageDTO<CategoryListResponseDTO> findCategoryList(Integer pages, Integer limit, String sortBy, String direction, String categoryName);
	
	public List<Category> findCategories(List<String> categoryIdList);
	
	public List<CategoryListResponseDTO> constructDTO(List<Category> categories);
	
	public Map<Long, List<String>> findCategoriesMap(List<Long> bookIdList);
}
