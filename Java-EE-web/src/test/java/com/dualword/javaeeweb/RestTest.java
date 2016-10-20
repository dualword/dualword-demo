package com.dualword.javaeeweb;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.net.URI;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RestTest {

	public static final String BASE_URI = "http://localhost:8080/Java-EE-web/rest/testrest";
	private static WebTarget target;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		URI uri = UriBuilder.fromUri(BASE_URI).build();
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		target = client.target(uri);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHello() {
		String res = target.path("hello").request(MediaType.APPLICATION_JSON).get(String.class);
		JsonObject json = Json.createReader(new StringReader(res)).readObject();
		assertEquals("fname", "Duke",json.getString("firstName"));		      
	}

}
