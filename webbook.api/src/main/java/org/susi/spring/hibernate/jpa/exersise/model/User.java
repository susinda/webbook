package org.susi.spring.hibernate.jpa.exersise.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class User {

	@Id
	private String email;
	private String firstName;
	private String lastName;
	private UserBase userBase;
	private String encodedPassword;
	private String cookie;
	
	//@ManyToOne
	//@JoinColumn(name = "THROTTLE_ID")
	//private ThrottleInfo throttleInfo;
	
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public UserBase getUserBase() {
		return userBase;
	}

	public void setUserBase(UserBase userBase) {
		this.userBase = userBase;
	}
	
	public String getEncodedPassword() {
		return encodedPassword;
	}

	public void setEncodedPassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}


	public User() {
	}

	public User(String email, String fName, String lName, UserBase base) {
		this.email = email;
		this.firstName = fName;
		this.lastName = lName;
		this.userBase = base;
	}
	

}
