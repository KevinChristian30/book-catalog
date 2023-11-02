package com.learning.bookcatalog.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.bookcatalog.domain.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long>{
	public Optional<Publisher> findBySecureId(String secureId);
	
	public Page<Publisher> findByNameLikeIgnoreCase(String name, Pageable pageable);
}
