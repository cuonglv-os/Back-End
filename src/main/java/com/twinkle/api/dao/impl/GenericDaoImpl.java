package com.twinkle.api.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.twinkle.api.dao.GenericDao;
import com.twinkle.api.domain.BaseDomain;

import lombok.extern.log4j.Log4j2;

@Log4j2
/**
 * 
 * @author cuonglv
 *
 * @param <E>  Class tuong ung voi Entity
 * @param <PK> Kieu du lieu cua khoa chinh
 */
public abstract class GenericDaoImpl<E, PK extends Serializable> implements GenericDao<E, PK>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	SessionFactory sessionFactory;

	public Session getCurrentSession() {
		try {
			return sessionFactory.getCurrentSession();
		} catch (Exception e) {
			return sessionFactory.openSession();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public PK save(E entity) {
		try {
			Session session = getCurrentSession();

			PK pk = null;
			BaseDomain domainObj = (BaseDomain) entity;
			if (domainObj.getIdentifier() != null) {
				session.merge(domainObj);
				pk = (PK) domainObj.getIdentifier();
			} else {
				pk = (PK) session.save(domainObj);
			}
			// Flush changing data
			session.flush();

			return pk;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public PK saveTransient(E entity) {
		try {
			Session session = getCurrentSession();

			PK pk = null;
			BaseDomain domainObj = (BaseDomain) entity;
			pk = (PK) session.save(domainObj);
			// Flush changing data
			session.flush();

			return pk;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}

	@Override
	public void update(E entity) {
		try {
			Session session = getCurrentSession();

			BaseDomain domainObj = (BaseDomain) entity;

			if (domainObj.getIdentifier() != null) {
				session.merge(domainObj);
			} else {
				session.update(entity);
			}

			// Flush changing data
			session.flush();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void saveOrUpdate(E entity) {
		try {
			Session session = getCurrentSession();

			BaseDomain domainObj = (BaseDomain) entity;
			if (domainObj.getIdentifier() != null) {
				session.merge(domainObj);
			} else {
				domainObj.setIdentifier(null);
				session.saveOrUpdate((E) domainObj);
			}
			// Flush changing data
			session.flush();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}

	@Override
	public void delete(E entity) {
		try {
			Session session = getCurrentSession();

			session.delete(entity);

			// Flush changing data
			session.flush();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}

	@Override
	public E findById(PK id) {
		try {
			return (E) getCurrentSession().get(getEntityClass(), id);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<E> findAll() {
		try {
			return (List<E>) createCriteria().list();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}

	@SuppressWarnings("deprecation")
	protected Criteria createCriteria() {
		Session session = getCurrentSession();
		return session.createCriteria(getEntityClass());
	}

	/**
	 * Get name of class
	 * 
	 * @return
	 */
	protected abstract Class<E> getEntityClass();
}
