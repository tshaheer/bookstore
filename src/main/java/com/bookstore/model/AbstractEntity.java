package com.bookstore.model;

import java.io.Serializable;

/**
 * Base abstract class for entities which will hold definitions for id.
 * 
 * @author Shaheer
 */
public abstract class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
