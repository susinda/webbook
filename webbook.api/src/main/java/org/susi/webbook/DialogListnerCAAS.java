package org.susi.webbook;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class DialogListnerCAAS {

	@GET
	@Path("/notification")
	@Produces("application/json")
	public Response smsGet() {
		System.out.println("GET /caas/notification");
		String result = "{\"Version\":\"v1.0\"}";
		return Response.ok().entity(result).build();
	}

	@POST
	@Path("/notification")
	@Produces("application/json")
	@Consumes("application/json")
	public Response smsPost(String input) {
		System.out.println("POST caas/notification request " + input);
		String result = "{\"Version\":\"v1.0\"}";
		return Response.ok().entity(result).build();
	}
	
}