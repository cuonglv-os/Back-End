package com.twinkle.api.dao;

import com.twinkle.api.domain.PersistentLogin;
import com.twinkle.api.domain.User;

/**
 * @author cuonglv
 *
 */
public interface PersistentLoginDao extends GenericDao<PersistentLogin, Long>{
	PersistentLogin createPersistentLogin(PersistentLogin persistentLogin);
	
	PersistentLogin findByUserIdAndStatus(User user, boolean status);
	
	PersistentLogin findByToken(String token);
}
