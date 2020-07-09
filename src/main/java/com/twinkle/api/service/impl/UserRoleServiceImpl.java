package com.twinkle.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twinkle.api.dao.UserRoleDao;
import com.twinkle.api.domain.UserRole;
import com.twinkle.api.service.UserRoleService;

/**
 * @author cuonglv
 *
 */
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	private UserRoleDao userRoleDao;
	
	@Override
	public UserRole create(UserRole userRole) {
		return userRoleDao.createUserRole(userRole);
	}

}
