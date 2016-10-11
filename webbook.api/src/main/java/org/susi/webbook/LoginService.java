package org.susi.webbook;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


@Path("/user")
public class LoginService {

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
        String name = "";
        String email = "";
        String result  = "{'Result':'OK'}";
        //System.out.println("User " + name + " registered with " + email);
        return Response.ok().entity(result).build();
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
}

