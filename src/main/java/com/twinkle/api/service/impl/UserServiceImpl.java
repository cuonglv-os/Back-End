package com.twinkle.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twinkle.api.dao.UserDao;
import com.twinkle.api.domain.User;
import com.twinkle.api.service.UserService;

/**
 * @author cuonglv
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public User findById(Long id) {
		return userDao.findById(id);
	}
	
	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public User create(User user) {
		return userDao.createUser(user);
	}

	@Override
	public boolean delete(Long id) {
		return userDao.deleteUser(id);
	}
}
