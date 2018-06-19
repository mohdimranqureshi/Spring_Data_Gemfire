package com.dish.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.Id;

/**
 * @author Mohd Imran
 * Base class for persistent classes.
 * 
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class AbstractPersistentEntity implements Serializable {

	@Id
	@javax.persistence.Id
	private final Long id;

	/**
	 * Returns the identifier of the entity.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	protected AbstractPersistentEntity(Long id) {
		this.id = id;
	}
	
	protected AbstractPersistentEntity() {
		this.id = null;
	}
	
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
			return false;
		}

		AbstractPersistentEntity that = (AbstractPersistentEntity) obj;

		return this.id.equals(that.getId());
	}
	
	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}
}
