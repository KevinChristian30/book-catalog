package com.learning.bookcatalog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.learning.bookcatalog.domain.Category;
import com.learning.bookcatalog.dto.CategoryQueryDTO;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, String>{
	public Optional<Category> findByCode(String code);
	
	public Page<Category> findByNameLikeIgnoreCase(String categoryName, Pageable pageable);

	public List<Category> findBySecureIdIn(List<String> categoryIdList);
	
	public List<Category> findByCodeIn(List<String> categoryIdList);
	
	@Query("SELECT new com.learning.bookcatalog.dto.CategoryQueryDTO(b.id, bc.code) " 
			+ "FROM Book b "
			+ "JOIN b.categories bc "
			+ "WHERE b.id IN :bookIdList")
	public List<CategoryQueryDTO> findCategoryByBookIdList(List<Long> bookIdList);
}
