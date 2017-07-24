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
import org.susi.spring.hibernate.jpa.exersise.model.Trip;
import org.susi.spring.hibernate.jpa.exersise.service.TripService;

@Path("/trip")
public class TripServiceWeb {

	public TripServiceWeb() {
		System.out.println("TripService cctor ");
	}

	@GET
	@Path("/{tripid}")
	@Produces("text/plain")
	public String ping(@PathParam("input") String input) {
		return input;
	}

	@POST
	@Path("/addtrip")
	@Produces("application/json")
	@Consumes("application/json")
	public Response registerTrip(String input) {

		System.out.println("User registration request " + input);
		JSONObject jObject = null;
		String start = "";
		String end = "";
		String driver = "";
		String sheduledTime = "";
		
		try {
			jObject = new JSONObject(input);
			start = (String) jObject.get("start");
			end = (String) jObject.get("end");
			driver = (String) jObject.get("driver");
			sheduledTime = (String) jObject.get("sheduledTime");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		Trip trip1 = new Trip(start, end, driver, sheduledTime);
		TripService tripService = (TripService) SpringApplication.getTripService();
		tripService.add(trip1);
		
		return Response.ok().entity("ok result ").build();
	}
}