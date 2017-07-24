package org.susi.spring.hibernate.jpa.exersise.main;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.susi.spring.hibernate.jpa.exersise.model.User;
import org.susi.spring.hibernate.jpa.exersise.model.UserBase;
import org.susi.spring.hibernate.jpa.exersise.service.UserService;

public class TestMain {
	
	public static void main(String[] args) {
		
		//Create Spring application context
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/spring.xml");
		
		//Get service from context. (service's dependency (ProductDAO) is autowired in UserService)
		UserService userService = (UserService) ctx.getBean("UserService");
		
		//Do some data operation
//		ThrottleInfo ti = new ThrottleInfo();
//		ti.setCurCount(0);
//		ti.setMaxCount(500);
//		ti.setTimeWindow(30);
//		ti.setTimeWindowUnit(TimeUnit.DAYS);
//		ti.setLastResetDate(new Timestamp(System.currentTimeMillis()));
		
//		ThrottleInfoServiceImpl throttleInfoService = (ThrottleInfoServiceImpl) ctx.getBean("ThrottleInfoService");
//		throttleInfoService.add(ti);
		
		User user = new User("susi31@gmail.com", "Susi", "Dman", UserBase.INTERNAL);
		//user.setThrottleInfo(ti);
		//userService.add(user);
		
		User susi2 = userService.getUserById("susi2@gmail.com");
		//System.out.println("\n\n" + susi2.getThrottleInfo().getLastResetDate() + "\n\n");
		
		userService.updatePassword(susi2, "myencodedPassword");
		
		
		//User user2 = new User("susi3@gmail.com", "Susi", "333", UserBase.FACEBOOK);
		//user2.setThrottleInfo(ti);
		//userService.add(user2);
		
		System.out.println("listAll: " + userService.listAll());
		
		//Test transaction rollback (duplicated key)
//		try {
//			productService.addAll(Arrays.asList(new User(1, "Book"), new User(6, "Soap"), new User(7, "Computer")));
//		} catch (DataAccessException dataAccessException) {
//		}
		
		//Test element list after rollback
		System.out.println("listAll: " + userService.listAll());
		
		ctx.close();
		
	}
}
