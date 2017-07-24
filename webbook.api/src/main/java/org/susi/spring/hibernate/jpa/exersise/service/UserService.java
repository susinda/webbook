package org.susi.spring.hibernate.jpa.exersise.service;

import java.util.Collection;
import java.util.List;
import org.susi.spring.hibernate.jpa.exersise.model.User;

public interface UserService {

	public void add(User product);
	public void addAll(Collection<User> products);
	public List<User> listAll();
	public User getUserById(String email);
	void updatePassword(User user, String password);

}



