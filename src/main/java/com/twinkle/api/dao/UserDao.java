package com.twinkle.api.dao;

import com.twinkle.api.domain.User;

/**
 * @author cuonglv
 *
 */
public interface UserDao extends GenericDao<User, Long>{
	User createUser(User user);
	
	User findByUsername(String username);
	
	boolean deleteUser(Long id);
}
