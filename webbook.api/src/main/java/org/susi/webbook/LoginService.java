package org.susi.webbook;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/user")
public class LoginService {
	
	CarpoolDAO carpoolDAO;
	public LoginService () {
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
	public Response serviceVersion() {
		String result = "{\"Version\":\"v1.0\"}";
		return Response.ok().entity(result).build();
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
		try {
			jObject = new JSONObject(input);
			name = (String) jObject.get("Name");
			email = (String) jObject.get("Email");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String result;
		if (name != null && email != null) {
			carpoolDAO.addNewUser(name, name, email);
			String user = carpoolDAO.getUser(email);
			System.out.println("User registration ok " + user);
			result = "{'Result':'OK'}";
		} else {
			result = "{'Result':'ERROR'}";
		}
		return Response.ok().entity(result).build();
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

}
