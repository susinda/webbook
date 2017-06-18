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

@Path("/")
public class DialogListnerSMS {

	@GET
	@Path("/receive")
	@Produces("application/json")
	public Response smsGet() {
		System.out.println("GET /sms/receive");
		String result = "{\"Version\":\"v1.0\"}";
		return Response.ok().entity(result).build();
	}

	@POST
	@Path("/receive")
	@Produces("application/json")
	@Consumes("application/json")
	public Response smsPost(String input) {
		System.out.println("POST sms/receive request " + input);
		String result = "{\"Version\":\"v1.0\"}";
		return Response.ok().entity(result).build();
	}
	
	
	@GET
	@Path("/dlr")
	@Produces("application/json")
	public Response dlrGet() {
		System.out.println("GET /sms/dlr");
		String result = "{\"Version\":\"v1.0\"}";
		return Response.ok().entity(result).build();
	}

	@POST
	@Path("/dlr")
	@Produces("application/json")
	@Consumes("application/json")
	public Response dlrPost(String input) {
		System.out.println("POST sms/dlr request " + input);
		String result = "{\"Version\":\"v1.0\"}";
		return Response.ok().entity(result).build();
	}

}