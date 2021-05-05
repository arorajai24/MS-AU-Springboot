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
		logger.info("User " + authUser.getName()+ " is signing in as : "+authUser.getEmail());
		authuserdao.saveAuthUser(authUser);
		logger.info(authUser.getName()+" successfully Signed In");
        return new Response("Success","Data Stored");
	}
	
	@GetMapping("/removeResponse")
	@CrossOrigin
	public Response removeResponse(@RequestHeader String id, @RequestHeader String authorization)
	{
		if(api.authenticate(id, authorization))
		{	
			logger.info("User by id : "+ id +" is logging out");
			authuserdao.removeResponse(id);
			logger.info("Logged Out Successfully.");
        	return new Response("Success","Logged Out");
		}
		else
		{
			logger.info("Access denied of logging out");
			return new Response("Access Denied","Failed");
		}
	}
}


