package com.learning.bookcatalog.domain;

import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "book")
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE book SET deleted = true WHERE id = ?")
public class Book extends AbstractBaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6262226520178211564L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "publisher_id", nullable = false)
	private Publisher publisher;
	
	@ManyToMany
	@JoinTable(
		name = "book_author",
		joinColumns = {
			@JoinColumn(name = "book_id", referencedColumnName = "id")
		},
		inverseJoinColumns = {
			@JoinColumn(name = "author_id", referencedColumnName = "id")
		}
	)
	private List<Author> authors;
	
	@ManyToMany
	@JoinTable(
		name = "book_category",
		joinColumns = {
			@JoinColumn(name = "book_id", referencedColumnName = "id")
		},
		inverseJoinColumns = {
			@JoinColumn(name = "category_code", referencedColumnName = "code")
		}
	)
	private List<Category> categories;
}
