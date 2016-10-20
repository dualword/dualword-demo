package com.dualword.javaeeweb.rest;


import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("testrest")
public class TestRest {
	
	public TestRest(){

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("hello")
	public String getString() {
		JsonObject json = Json.createObjectBuilder()
				.add("firstName", "Duke")
				.add("lastName", "Java")
				.build();
		return json.toString();
		
	}

}
