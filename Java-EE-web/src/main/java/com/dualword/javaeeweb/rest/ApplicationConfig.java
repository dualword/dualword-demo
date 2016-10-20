package com.dualword.javaeeweb.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/rest")
public class ApplicationConfig extends Application {
	
	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(TestRest.class);
		return classes;
	}	
	
}
