package com.dayside.dbrs.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.context.WebApplicationContext;


public class ServletInitializer extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(ServletInitializer.class);
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		
		System.setProperty("spring.config.location", "classpath:/config/spring/");				
		if (System.getProperty("spring.profiles.active") == null) {
			System.setProperty("spring.profiles.active", "local");
		}
		
		logger.info("## spring.profiles.active : " + System.getProperty("spring.profiles.active"));		
		return application.sources(ContextRoot.class);
	}

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		logger.info("onStartup");	
		super.onStartup(servletContext);
	}

	@Override
	protected WebApplicationContext createRootApplicationContext(
			ServletContext servletContext) {
		return super.createRootApplicationContext(servletContext);
	}

	@Override
	protected WebApplicationContext run(SpringApplication application) {
		logger.info("run");
		return super.run(application);
	}
	
	
}