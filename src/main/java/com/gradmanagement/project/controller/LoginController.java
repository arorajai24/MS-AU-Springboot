package com.gradmanagement.project.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.gradmanagement.project.model.AuthUser;
import com.gradmanagement.project.model.Response;
import com.gradmanagement.project.security.ApiGateway;
import com.gradmanagement.project.service.authUserDAO;

import ch.qos.logback.classic.Logger;

@RestController
public class LoginController {
	@Autowired
	authUserDAO authuserdao;
	
	@Autowired
	ApiGateway api; 
	
	Logger logger = (Logger) LoggerFactory.getLogger(LoginController.class);
	
	
	@PostMapping("/savesResponse")
	@CrossOrigin
	public Response Savesresponse(@RequestBody AuthUser authUser)
	{
		authuserdao.saveAuthUser(authUser);
		logger.info(authUser.getName() +" (" + authUser.getEmail()+") has successfully Signed In.");
        return new Response("Success","Data Stored");
	}
	
	@GetMapping("/removeResponse")
	@CrossOrigin
	public Response removeResponse(@RequestHeader String id, @RequestHeader String authorization)
	{
		if(api.authenticate(id, authorization))
		{	
			authuserdao.removeResponse(id);
			logger.info("User logged Out Successfully.");
        	return new Response("Success","Logged Out");
		}
		else
		{
			logger.info("Unknown Request, can't logout without authentication.");
			return new Response("Access Denied","Failed");
		}
	}
}


