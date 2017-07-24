package org.susi.spring.hibernate.jpa.exersise.dao;


//import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractJpaDao<T extends Object> {

	private Class<T> clazz;
	
	@PersistenceContext
	private EntityManager entityManager;

	public void setClazz(final Class<T> clazzToSet) {
		this.clazz = clazzToSet;
	}

	public T getById(final Long id) {
		return entityManager.find(clazz, id);
	}
	
	public T getById(final String id) {
		return entityManager.find(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return entityManager.createQuery("from " + clazz.getName())
				.getResultList();
	}

	public void save(final T entity) {
		try {
			entityManager.persist(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(final T entity) {
		entityManager.merge(entity);
	}

	public void delete(final T entity) {
		entityManager.remove(entity);
	}

	public void deleteById(final Long entityId) {
		final T entity = getById(entityId);
		delete(entity);
	}
}