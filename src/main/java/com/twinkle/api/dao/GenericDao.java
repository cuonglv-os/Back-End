package com.twinkle.api.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author cuonglv
 *
 */
public interface GenericDao<E, PK extends Serializable>{
	
	/**
	 * Save an object no ID
	 * @param entity
	 * @return
	 */
	PK save(E entity);

	/**
	 * Save an object in the state Transient, used when have ID
	 * @param entity
	 * @return
	 */
	PK saveTransient(E entity);
	
	/**
	 * Update an Object
	 * @param entity
	 */
	void update(E entity);
	
	/**
	 * Save if not existing, Update if already
	 * @param entity
	 */
	void saveOrUpdate(E entity);

	/**
	 * Delete an Object
	 * @param entity
	 */
	void delete(E entity);
	
	/**
	 * Find Object by primary key value
	 * @param id
	 * @return
	 */
	E findById(PK id);
	
	/**
	 * Find all objects in database
	 * @return
	 */
	List<E> findAll();
}
