package com.twinkle.api.dao.impl;

import java.io.Serializable;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.twinkle.api.dao.UserDao;
import com.twinkle.api.domain.User;

import lombok.extern.log4j.Log4j2;

/**
 * @author cuonglv
 *
 */
@Log4j2
@Repository("userDao")
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public User createUser(User user) {
		this.saveTransient(user);
		return user;
	}

	public User findByUsername(String username) {
		Session session = getCurrentSession();

		if (session == null) {
			return null;
		}

		try {
			Query query = session.createNamedQuery("User.findByUsername", User.class).setParameter("userName",
					username);

			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
	}

	@Override
	public boolean deleteUser(Long id) {
		boolean success = false;

		Session session = getCurrentSession();

		if (session == null) {
			return false;
		}

		try {
			session.beginTransaction();
			Query query = session.createNamedQuery("User.delete").setParameter("userId", id);

			success = query.executeUpdate() > 0;

			session.getTransaction().commit();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
			session.getTransaction().rollback();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

		return success;
	}

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}
}
