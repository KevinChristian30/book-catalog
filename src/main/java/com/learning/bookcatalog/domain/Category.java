package com.learning.bookcatalog.domain;

import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "category")
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE category SET deleted = true WHERE id = ?")
public class Category extends AbstractBaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3500702833591901036L;

	@Id
	@Column(name = "code", nullable = false)
	private String code;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "description", nullable = true)
	private String description;
	
	@ManyToMany(mappedBy = "categories")
	private List<Book> books;
}
