package com.twinkle.api.service;

import com.twinkle.api.domain.PersistentLogin;

/**
 * @author cuonglv
 *
 */
public interface PersistentLoginService {
	PersistentLogin add(PersistentLogin persistentLogin);
	
	PersistentLogin findByToken(String token);
	
	void update(PersistentLogin persistentLogin);
}
