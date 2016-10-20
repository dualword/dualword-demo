package com.dualword.javaeeweb;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener{

	public static String UPTIME = "uptime";
	
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		arg0.getServletContext().setAttribute(ContextListener.UPTIME, System.currentTimeMillis());
		
	}

}
