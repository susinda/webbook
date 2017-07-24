package org.susi.spring.hibernate.jpa.exersise.dao;

import java.util.List;

import org.susi.spring.hibernate.jpa.exersise.model.User;

public interface UserDao {

	public void saveUser(User user);
	public List<User> getAllUsers();
	public User getUserById(String email);
	void updatePassword(User user, String encPass);
}


