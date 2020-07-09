package com.twinkle.api.service;

import com.twinkle.api.domain.User;

/**
 * @author cuonglv
 *
 */
public interface AuthService {
	boolean checkLogin(String username, String password, User userDb);
	
	String getTokenFromDb(User user);
}
