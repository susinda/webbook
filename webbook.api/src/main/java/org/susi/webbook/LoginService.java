package org.susi.webbook;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.susi.spring.hibernate.jpa.exersise.main.SpringApplication;
import org.susi.spring.hibernate.jpa.exersise.model.User;
import org.susi.spring.hibernate.jpa.exersise.service.UserService;

@Path("/user")
public class LoginService {

	CarpoolDAO carpoolDAO;

	public LoginService() {
		System.out.println("LoginService cctor ");
		carpoolDAO = new CarpoolDAO();
	}

	@GET
	@Path("/echo/{input}")
	@Produces("text/plain")
	public String ping(@PathParam("input") String input) {
		return input;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/jsonBean")
	public Response modifyJson(JsonBean input) {
		input.setVal2(input.getVal1());
		return Response.ok().entity(input).build();
	}

	@GET
	@Path("/service/version")
	@Produces("application/json")
	public Response serviceVersion(@CookieParam("G_ENABLED_IDPS") String value) {

		System.out.println(value);
		if (value == null) {
			System.out.println("Response.serverError().entity(error).build();");
		} else {
			System.out.println(value);
		}
		NewCookie cookie = new NewCookie("name", "123");
		String result = "{\"Version\":\"v1.0\"}";
		return Response.ok().entity(result).cookie(cookie).build();
	}

	@POST
	@Path("/service/register")
	@Produces("application/json")
	@Consumes("application/json")
	public Response registerUser(String input) {

		System.out.println("User registration request " + input);
		JSONObject jObject = null;
		String name = null;
		String email = null;
		String pwd = null;
		try {
			jObject = new JSONObject(input);
			name = (String) jObject.get("Name");
			email = (String) jObject.get("Email");
			pwd = (String) jObject.get("Password");
		} catch (JSONException e) {
			e.printStackTrace();
		}

//		String result;
//		if (name != null && email != null) {
//			carpoolDAO.addNewUser(name, name, email, pwd);
//			String user = carpoolDAO.getUser(email);
//			System.out.println("User registration ok " + user);
//			result = "{'Result':'OK'}";
//		} else {
//			result = "{'Result':'ERROR'}";
//		}
		
		UserService userService = SpringApplication.getUserService();
		User testUser = new User();
		testUser.setFirstName(name);
		testUser.setEmail(email);
		testUser.setCookie(pwd);
		userService.add(testUser);
		
		return Response.ok().entity("ok result ").build();
	}

	@POST
	@Path("/service/location")
	@Produces("application/json")
	@Consumes("application/json")
	public Response location(String input) {

		System.out.println("Update location request " + input);
		JSONObject jObject = null;
		String phone = null;
		double lat = 0;
		double lon = 0;
		try {
			jObject = new JSONObject(input);
			phone = (String) jObject.get("Phone");
			lat = (double) jObject.get("Lat");
			lon = (double) jObject.get("Lon");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String result;
		if (phone != null) {
			carpoolDAO.updateLocation(phone, lat, lon);
			String location = carpoolDAO.getLocation(phone);
			System.out.println("Location Update ok " + location);
			result = "{'Result':'OK'}";
		} else {
			result = "{'Result':'ERROR'}";
		}
		return Response.ok().entity(result).build();
	}

	@GET
	@Path("/service/location")
	@Produces("application/json")
	public Response getLocation(@QueryParam("phone") String phone) {

		System.out.println("get location request " + phone);
		String location = carpoolDAO.getLocation(phone);
		return Response.ok().entity(location).build();
	}

	@GET
	@Path("/service/user")
	@Produces("application/json")
	public Response getUser(@QueryParam("email") String email) {

		System.out.println("get user request " + email);
		String location = carpoolDAO.getUser(email);
		return Response.ok().entity(location).build();
	}

	@POST
	@Path("/service/login")
	@Produces("application/json")
	@Consumes("application/json")
	public Response authenticate(String input) {

		System.out.println("User authentication request ");
		JSONObject jObject = null;
		String email = null;
		String password = null;
		try {
			jObject = new JSONObject(input);
			email = (String) jObject.get("Email");
			password = (String) jObject.get("Password");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println(email + " " + password);
		boolean res = carpoolDAO.verifyPassword(email, password);
		String result = "{'Result':" + res + "}";
		return Response.ok().entity(result).build();
	}

	@POST
	@Path("/service/login")
	@Produces("application/json")
	@Consumes("application/json")
	public Response registerRide(String input) {

		System.out.println("User authentication request ");
		JSONObject jObject = null;
		String email = null;
		String src = null;
		String dest = null;
		String time = null;
		String numPassengers = null;
		String cabNumber = null;
		try {
			jObject = new JSONObject(input);
			email = (String) jObject.get("Email");
			src = (String) jObject.get("Src");
			dest = (String) jObject.get("Dest");
			time = (String) jObject.get("Time");
			numPassengers = (String) jObject.get("NumPassengers");
			cabNumber = (String) jObject.get("CabNumber");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// boolean res = carpoolDAO.addRide(email, src, dest, cabNumber,
		// numPassengers);
		String result = "{'Result':" + "res" + "}";
		return Response.ok().entity(result).build();
	}

}
