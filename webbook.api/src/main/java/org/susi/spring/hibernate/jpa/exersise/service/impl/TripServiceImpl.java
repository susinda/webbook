package org.susi.spring.hibernate.jpa.exersise.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.susi.spring.hibernate.jpa.exersise.dao.TripDao;
import org.susi.spring.hibernate.jpa.exersise.model.Trip;
import org.susi.spring.hibernate.jpa.exersise.service.TripService;


@Service("TripService")
public class TripServiceImpl implements TripService {

	@Autowired
	private TripDao tripDao;

	@Override
	@Transactional
	public void add(Trip trip) {
		tripDao.add(trip);
	}

	@Override
	@Transactional
	public void update(Trip trip) {
		tripDao.update(trip);
	}

	@Override
	public Trip getByTripId(long tripId) {
		 return tripDao.getTripById(tripId);
	}

}