package com.twinkle.api.dao;

import com.twinkle.api.domain.UserRole;

/**
 * @author cuonglv
 *
 */
public interface UserRoleDao extends GenericDao<UserRole, Long>{
	UserRole createUserRole(UserRole userRole);
}
