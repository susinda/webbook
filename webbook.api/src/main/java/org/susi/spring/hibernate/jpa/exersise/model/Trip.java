package org.susi.spring.hibernate.jpa.exersise.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Trip {

	@Id
	private int tripId;
	private String start;
	private String end;
	private String driver;
	private UserBase pasenger;
	private String sheduledTime;

	public int getTripId() {
		return tripId;
	}
	public void setTripId(int tripId) {
		this.tripId = tripId;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public UserBase getPasenger() {
		return pasenger;
	}
	public void setPasenger(UserBase pasenger) {
		this.pasenger = pasenger;
	}
	public String getSheduledTime() {
		return sheduledTime;
	}
	public void setSheduledTime(String sheduledTime) {
		this.sheduledTime = sheduledTime;
	}
	
	
	public Trip() {
	
	}

	
	public Trip(String start, String end, String driver, String sheduledTime) {
		this.start = start;
		this.end = end;
		this.driver = driver;
		this.sheduledTime = sheduledTime;
	}
}
