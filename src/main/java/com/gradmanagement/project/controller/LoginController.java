package com.gradmanagement.project.controller;

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

@RestController
public class LoginController {
	@Autowired
	authUserDAO authuserdao;
	
	@Autowired
	ApiGateway api;
	
	@PostMapping("/savesResponse")
	@CrossOrigin
	public Response Savesresponse(@RequestBody AuthUser authUser)
	{
		authuserdao.saveAuthUser(authUser);
        return new Response("success","Data Stored");
	}
	
	@GetMapping("/removeResponse")
	@CrossOrigin
	public Response removeResponse(@RequestHeader String id, @RequestHeader String authorization)
	{
		if(api.authenticate(id, authorization))
		{	
			authuserdao.removeResponse(id);
        	return new Response("Success","Logged Out");
		}
		else
			return new Response("Access Denied","Failed");
	}
}


