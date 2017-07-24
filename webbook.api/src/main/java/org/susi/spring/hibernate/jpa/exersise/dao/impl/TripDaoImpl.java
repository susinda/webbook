package org.susi.spring.hibernate.jpa.exersise.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.susi.spring.hibernate.jpa.exersise.dao.AbstractJpaDao;
import org.susi.spring.hibernate.jpa.exersise.dao.TripDao;
import org.susi.spring.hibernate.jpa.exersise.dao.UserDao;
import org.susi.spring.hibernate.jpa.exersise.model.Trip;
import org.susi.spring.hibernate.jpa.exersise.model.User;



@Repository
public class TripDaoImpl extends AbstractJpaDao<Trip> implements TripDao {
	
	public TripDaoImpl() {
		setClazz(Trip.class);
	}

	@Override
	public void add(Trip trip) {
		save(trip);
	}

	@Override
	public List<Trip> getAllTrips() {
		return findAll();
	}

	@Override
	public Trip getTripById(long tripId) {
	     return getById(tripId);
	}

	@Override
	public void update(Trip trip) {
		//User user = getById(email);
		//user.setEncodedPassword(encPass);
		//update(user);
	}
}