package com.bookstore.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

import com.bookstore.exception.DataAccessException;

/**
 * Interface for generic CRUD operations on a domain object for a specific type.
 *
 * @param <T>
 *            The type of the domain object for which this instance is to be
 *            used.
 * @param <ID>
 *            The type of the id of the domain object for which this instance is
 *            to be used.
 * 
 * @author Shaheer
 */
public interface GenericDao<T, ID extends Serializable> {

	/**
	 * Retrieves an entity by its id.
	 *
	 * @param id
	 *            must not be {@literal null}.
	 * @return the entity with the given id or {@literal Optional#empty()} if none
	 *         found
	 * @throws IllegalArgumentException
	 *             if {@code id} is {@literal null}.
	 * @throws DataAccessException
	 *             If something fails at database level.
	 */
	Optional<T> findById(ID id);

	/**
	 * Returns all instances of the type.
	 *
	 * @return all entities
	 * @throws DataAccessException
	 *             If something fails at database level.
	 */
	Collection<T> findAll();

	/**
	 * Saves a given entity.
	 * 
	 * <p>
	 * If the id of the entity is {@literal null} or zero, add it to the data store
	 * and assign it an id; otherwise, update the corresponding entity in the data
	 * store with the properties of this entity.
	 * 
	 * @param entity
	 *            must not be {@literal null}.
	 * @return the saved entity will never be {@literal null}. * @throws
	 *         IllegalArgumentException in case the given entity is {@literal null}.
	 * @throws DataAccessException
	 *             If something fails at database level.
	 */
	T save(T entity);

	/**
	 * Deletes a given entity.
	 *
	 * @param entity
	 *            must not be {@literal null}.
	 * @throws IllegalArgumentException
	 *             in case the given entity is {@literal null}.
	 * @throws DataAccessException
	 *             If something fails at database level.
	 */
	void delete(T entity);

	/**
	 * Deletes the entity with the given id.
	 *
	 * @param id
	 *            must not be {@literal null}.
	 * @throws IllegalArgumentException
	 *             in case the given {@code id} is {@literal null}
	 * @throws DataAccessException
	 *             If something fails at database level.
	 */
	void deleteById(ID id);
	
	/**
	 * Returns the number of entities available.
	 *
	 * @return the number of entities
	 */
	long count();

}
