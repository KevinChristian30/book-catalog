package com.learning.bookcatalog.domain;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "author")
//@Where(clause = "deleted=false")
//@SQLDelete(sql = "UPDATE author SET deleted = true WHERE id = ?")
public class Author extends AbstractBaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7588618719064495000L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_generator")
	@SequenceGenerator(name = "author_generator", sequenceName = "author_id_seq")
	private Long id;
	
	@Column(name = "name", nullable = false, columnDefinition = "varchar(300)")
	private String name;
	
	@Column(name = "date_of_birth", nullable = false)
	private LocalDate dateofBirth;
	
	@ManyToMany
	@JoinTable(
		name = "book_author", 
		joinColumns = {
			@JoinColumn(name = "author_id", referencedColumnName = "id")	
		},
		inverseJoinColumns = {
			@JoinColumn(name = "book_id", referencedColumnName = "id")
		}
	)
	private List<Book> books;
	
	@OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
	private List<Address> addresses;
}
