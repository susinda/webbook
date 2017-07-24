package org.susi.spring.hibernate.jpa.exersise.dao;

import java.util.List;

import org.susi.spring.hibernate.jpa.exersise.model.Trip;

public interface TripDao {

	public void add(Trip trip);
	public List<Trip> getAllTrips();
	public Trip getTripById(long tripId);
	void update(Trip trip);
}


