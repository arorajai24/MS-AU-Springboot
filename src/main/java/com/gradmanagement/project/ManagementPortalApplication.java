package com.gradmanagement.project;


import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gradmanagement.project.controller.RESTController;

import ch.qos.logback.classic.Logger;

@SpringBootApplication
public class ManagementPortalApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ManagementPortalApplication.class, args);
		Logger logger = (Logger) LoggerFactory.getLogger(ManagementPortalApplication.class);
		logger.info("Application started");
	}

}
