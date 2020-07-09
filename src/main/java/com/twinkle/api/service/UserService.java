package com.twinkle.api.service;

import java.util.List;

import com.twinkle.api.domain.User;

/**
 * @author cuonglv
 *
 */
public interface UserService {
	/**
	 * GET ALL USER
	 */
	List<User> findAll();
	
	/**
	 * GET USER BY ID
	 * @param id
	 */
	User findById(Long id);
	
	/**
	 * GET USER BY USERNAME
	 * @param username
	 */
	User findByUsername(String username);
	
	/**
	 * CREATE NEW USER
	 * @param user
	 */
	User create(User user);
	
	/**
	 * DELETE USER
	 * @param id
	 */
	boolean delete(Long id);
}
