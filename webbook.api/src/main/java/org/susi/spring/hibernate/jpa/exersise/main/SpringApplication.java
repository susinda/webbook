package org.susi.spring.hibernate.jpa.exersise.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.susi.spring.hibernate.jpa.exersise.service.TripService;
import org.susi.spring.hibernate.jpa.exersise.service.UserService;

public class SpringApplication {

	static ClassPathXmlApplicationContext ctx;
	static SpringApplication instance = new SpringApplication();
	
	public static SpringApplication getInstance() {
		return instance;
	}
	
	private SpringApplication() {
		System.out.println("SpringApplication cctor");
		ctx = new ClassPathXmlApplicationContext("classpath:/spring.xml");
		ctx.registerShutdownHook();
	}

	public static UserService getUserService() {
		UserService userService = (UserService) ctx.getBean("UserService");
		return userService;
	}
	
	public static TripService getTripService() {
		TripService tripService = (TripService) ctx.getBean("TripService");
		return tripService;
	}

	public void close() {
		ctx.close();
	}
}
