package com.learning.bookcatalog.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.learning.bookcatalog.domain.Category;
import com.learning.bookcatalog.dto.CategoryCreateAndUpdateRequestDTO;
import com.learning.bookcatalog.dto.CategoryListResponseDTO;
import com.learning.bookcatalog.dto.CategoryQueryDTO;
import com.learning.bookcatalog.dto.ResultResponsePageDTO;
import com.learning.bookcatalog.exception.BadRequestException;
import com.learning.bookcatalog.repository.CategoryRepository;
import com.learning.bookcatalog.service.CategoryService;
import com.learning.bookcatalog.util.PaginationUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{
	private CategoryRepository categoryRepository;
	
	@Override
	public void createAndUpdateCategory(CategoryCreateAndUpdateRequestDTO dto) {
		Category category = categoryRepository
			.findByCode(dto.getCode())
			.orElse(new Category());
	
		if (category.getCode() == null) {
			category.setCode(dto.getCode());
		}
		
		category.setName(dto.getName());
		category.setDescription(dto.getDescription());
		category.setDeleted(false);
		
		categoryRepository.save(category);
	}

	@Override
	public ResultResponsePageDTO<CategoryListResponseDTO> findCategoryList(Integer pages, Integer limit, String sortBy,
			String direction, String categoryName) {
		categoryName = StringUtils.isEmpty(categoryName) ? "%" : categoryName + "%";
		
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(direction), sortBy));
		Pageable pageable = PageRequest.of(pages, limit, sort);
		
		Page<Category> pageResult = categoryRepository.findByNameLikeIgnoreCase(categoryName, pageable);
		
		List<CategoryListResponseDTO> dtos = pageResult.stream().map(c -> {
			CategoryListResponseDTO dto = new CategoryListResponseDTO();
			
			dto.setCode(c.getCode());
			dto.setName(c.getName());
			dto.setDescription(c.getDescription());
			
			return dto;
		}).collect(Collectors.toList());
		
		return PaginationUtil.createResultPage(dtos, pageResult.getTotalElements(), (long) pageResult.getTotalPages());
	}

	@Override
	public List<Category> findCategories(List<String> categoryIdList) {
		List<Category> categories = categoryRepository.findByCodeIn(categoryIdList);
		if (categories.isEmpty()) {
			throw new BadRequestException("categories is empty");
		}
		
		return categories;
	}

	@Override
	public List<CategoryListResponseDTO> constructDTO(List<Category> categories) {
		return categories.stream().map(e -> {
			CategoryListResponseDTO dto = new CategoryListResponseDTO();
			
			dto.setCode(e.getCode());
			dto.setName(e.getName());
			dto.setDescription(e.getDescription());
			
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public Map<Long, List<String>> findCategoriesMap(List<Long> bookIdList) {
		List<CategoryQueryDTO> queryList = categoryRepository.findCategoryByBookIdList(bookIdList);
		Map<Long, List<String>> categoryMaps = new HashMap<>();
		List<String> categoryCodeList = null;
		
		for (CategoryQueryDTO dto : queryList) {
			if (!categoryMaps.containsKey(dto.getBookId())) {
				categoryCodeList = new ArrayList<>();
			} else {
				categoryCodeList = categoryMaps.get(dto.getBookId());
			}
			
			categoryCodeList.add(dto.getCategoryCode());
			categoryMaps.put(dto.getBookId(), categoryCodeList);
		}
		
		return categoryMaps;
	}
}
