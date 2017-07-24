package org.susi.spring.hibernate.jpa.exersise.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.susi.spring.hibernate.jpa.exersise.dao.AbstractJpaDao;
import org.susi.spring.hibernate.jpa.exersise.dao.UserDao;
import org.susi.spring.hibernate.jpa.exersise.model.User;



@Repository
public class UserDaoImpl extends AbstractJpaDao<User> implements UserDao {
	
	public UserDaoImpl() {
		setClazz(User.class);
	}

	@Override
	public void saveUser(User product) {
		save(product);
	}

	@Override
	public List<User> getAllUsers() {
		return findAll();
	}

	@Override
	public User getUserById(String email) {
	     return getById(email);
	}

	@Override
	public void updatePassword(User user, String encPass) {
		//User user = getById(email);
		user.setEncodedPassword(encPass);
		update(user);
	}

//	@Override
//	public void updateCookie(String cookie) {
//		
//	}
//	


//	@PersistenceContext
//	private EntityManager em;
//
//	public void persist(User product) {
//		em.persist(product);
//	}
//
//	public List<User> findAll() {
//		return em.createQuery("SELECT p FROM User p").getResultList();
//	}
	
	

}