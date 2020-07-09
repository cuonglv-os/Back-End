package com.twinkle.api.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.twinkle.api.dao.UserRoleDao;
import com.twinkle.api.domain.UserRole;

/**
 * @author cuonglv
 *
 */
@Repository("userRoleDao")
public class UserRoleDaoImpl extends GenericDaoImpl<UserRole, Long> implements UserRoleDao, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public UserRole createUserRole(UserRole userRole) {
		this.save(userRole);
		return userRole;
	}

	@Override
	protected Class<UserRole> getEntityClass() {
		return UserRole.class;
	}
	
}
