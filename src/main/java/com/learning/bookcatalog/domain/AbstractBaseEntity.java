package com.learning.bookcatalog.domain;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Index;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@MappedSuperclass
@Table(
	indexes = {
			@Index(name = "uk_secure_id", columnList = "secure_id")
	}
)
public abstract class AbstractBaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1078772089835319165L;

	@Column(name = "secure_id", nullable = false, unique = true)
	private String secureId = UUID.randomUUID().toString();
	
	@Column(name = "deleted", columnDefinition = "boolean default false")
	private Boolean deleted;
}
