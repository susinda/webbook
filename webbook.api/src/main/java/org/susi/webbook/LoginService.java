package org.susi.webbook;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

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
       
        JSONObject jObject = null;
        String name = null;
        String email = null;
		try {
			jObject = new JSONObject(input);
			name = (String) jObject.get("Name");
			email = (String) jObject.get("Email");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		String result;
        if (name != null && email != null) {
        	 insertSQL(name, name, email);
             result  = "{'Result':'OK'}";
        } else {
             result  = "{'Result':'ERROR'}";
        }
       
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
    
    
    private void insertSQL(String fName, String lName, String email) {
    try {
      // create a mysql database connection
      String myDriver = "com.mysql.jdbc.Driver";
      String myUrl = "jdbc:mysql://localhost:3306/users";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "root", "root");
    
      // the mysql insert statement
      String query = " insert into Persons (FirstName, LastName, Email) values (?, ?, ?)";

      // create the mysql insert preparedstatement
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setString (1, fName);
      preparedStmt.setString (2, lName);
      preparedStmt.setString (3, email);
      preparedStmt.execute();
      
      conn.close();
    }
    catch (Exception e) {
      System.err.println("Got an exception!");
      System.err.println(e.getMessage());
    }
  }
    
}



