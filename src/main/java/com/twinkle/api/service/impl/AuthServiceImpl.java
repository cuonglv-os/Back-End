package com.twinkle.api.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.twinkle.api.dao.PersistentLoginDao;
import com.twinkle.api.domain.PersistentLogin;
import com.twinkle.api.domain.User;
import com.twinkle.api.service.AuthService;

/**
 * @author cuonglv
 *
 */
@Service("authService")
public class AuthServiceImpl implements AuthService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private PersistentLoginDao persistentLoginDao;

//	static {
//		User userKai = new User(1L, "kai", "123456");
//		Set<Role> set1 = new HashSet<>();
//		set1.add(new Role(1L, "ROLE_ADMIN"));
//		userKai.setRoles(set1);
//		Set<Role> set2 = new HashSet<>();
//		set2.add(new Role(2L, "ROLE_USER"));
//		User userSena = new User(2L, "sena", "123456");
//		userSena.setRoles(set2);
//		listUser.add(userKai);
//		listUser.add(userSena);
//	}

	@Override
	public boolean checkLogin(String username , String password, User userDb) {
		if(Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password) || userDb == null) {
			return false;
		}
		
		if (StringUtils.equals(username, userDb.getUserName())
				&& StringUtils.equals(passwordEncoder.encode(password), userDb.getPassword())) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public String getTokenFromDb(User user) {
		PersistentLogin persistentLogin = persistentLoginDao.findByUserIdAndStatus(user, true);
		
		if(persistentLogin == null) {
			return null;
		}
		
		return persistentLogin.getToken();
	}

}
