package com.learning.bookcatalog.domain;

import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "publisher")
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE publisher SET deleted = true WHERE id = ?")
public class Publisher extends AbstractBaseEntity{/**
	 * 
	 */
	private static final long serialVersionUID = -3630312818909261736L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publisher_generator")
	@SequenceGenerator(name = "publisher_generator", sequenceName = "publisher_id_seq")
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "company_name", nullable = false)
	private String companyName;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@OneToMany(mappedBy = "publisher")
	private List<Book> books;
}
