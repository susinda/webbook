package org.susi.spring.hibernate.jpa.exersise.service;

import org.susi.spring.hibernate.jpa.exersise.model.Trip;

public interface TripService {

	public void add(Trip trip);
	public void update(Trip trip);
	public Trip getByTripId(long tripId);
}



