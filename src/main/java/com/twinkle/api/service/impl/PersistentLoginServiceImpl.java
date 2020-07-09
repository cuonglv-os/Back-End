package com.twinkle.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twinkle.api.dao.PersistentLoginDao;
import com.twinkle.api.domain.PersistentLogin;
import com.twinkle.api.service.PersistentLoginService;

/**
 * @author cuonglv
 *
 */
@Service("persistentLoginService")
public class PersistentLoginServiceImpl implements PersistentLoginService{

	@Autowired
	private PersistentLoginDao persistentLoginDao;
	
	@Override
	public PersistentLogin add(PersistentLogin persistentLogin) {
		return persistentLoginDao.createPersistentLogin(persistentLogin);
	}

	@Override
	public PersistentLogin findByToken(String token) {
		return persistentLoginDao.findByToken(token);
	}

	@Override
	public void update(PersistentLogin persistentLogin) {
		if(persistentLogin != null) {
			persistentLoginDao.update(persistentLogin);
		}
	}

}
