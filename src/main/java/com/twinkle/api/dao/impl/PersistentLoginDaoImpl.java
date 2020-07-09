package com.twinkle.api.dao.impl;

import java.io.Serializable;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.google.common.base.Strings;
import com.twinkle.api.dao.PersistentLoginDao;
import com.twinkle.api.domain.PersistentLogin;
import com.twinkle.api.domain.User;

/**
 * @author cuonglv
 *
 */
@Repository("persistentLoginDao")
public class PersistentLoginDaoImpl extends GenericDaoImpl<PersistentLogin, Long>
		implements PersistentLoginDao, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public PersistentLogin createPersistentLogin(PersistentLogin persistentLogin) {
		this.saveTransient(persistentLogin);
		return persistentLogin;
	}

	@Override
	public PersistentLogin findByUserIdAndStatus(User user, boolean status) {
		Session session = getCurrentSession();

		if (session == null || user == null || user.getUserId() == null) {
			return null;
		}

		try {
			Query query = session.createNamedQuery("PersistentLogin.findByUserIdAndStatus", PersistentLogin.class)
					.setParameter("userId", user.getUserId()).setParameter("status", status);

			return (PersistentLogin) query.getSingleResult();
		} catch (NoResultException e) {
			return new PersistentLogin();
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}

	@Override
	public PersistentLogin findByToken(String token) {
		Session session = getCurrentSession();

		if (session == null || Strings.isNullOrEmpty(token)) {
			return null;
		}

		try {
			Query query = session.createNamedQuery("PersistentLogin.findByToken", PersistentLogin.class)
					.setParameter("token", token);

			return (PersistentLogin) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}

	@Override
	protected Class<PersistentLogin> getEntityClass() {
		return PersistentLogin.class;
	}
}
