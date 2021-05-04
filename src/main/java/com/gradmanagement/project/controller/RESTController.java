package com.gradmanagement.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.gradmanagement.project.model.User;
import com.gradmanagement.project.security.ApiGateway;
import com.gradmanagement.project.service.UserDAO;

@RestController
public class RESTController {

	@Autowired
	private UserDAO userdao;
	
	@Autowired
	ApiGateway api;  // implement authenticate functions...!!
	
//	@GetMapping("/")
//	@ResponseBody
//	public String home()
//	{
//		return "hi...";
//	}
	
	@GetMapping("/allusers")
	@CrossOrigin
	public Iterable<User> listGrads(@RequestHeader String id, @RequestHeader String authorization)
	{
		if(api.authenticate(id, authorization))
		{
			return userdao.listGrad();
		}
		return null;
	}
	
	@RequestMapping("/delete-user/{id}")
	@CrossOrigin
	public void deleteById(@PathVariable(name="id") int tmp, @RequestHeader String id, @RequestHeader String authorization)
	{
		if(api.authenticate(id, authorization))
		{
			userdao.deleteGrad(tmp);
		}
		
	}
	
	@PostMapping("/save-user")
	@Transactional
	@CrossOrigin
	public String register(@RequestBody User user, @RequestHeader String id, @RequestHeader String authorization)
	{
		if(api.authenticate(id, authorization))
		{
			userdao.registerUser(user);
			return user.getFname() + " has been registered successfully.";
		}
		return "Access Denied";
	}
	
	@RequestMapping("/search/{fname}")
	@Transactional
	@CrossOrigin
	public Iterable<User> searchByFirstname(@PathVariable(name="fname") String searchVar, @RequestHeader String id, @RequestHeader String authorization)
	{
		if(api.authenticate(id, authorization))
		{
			return userdao.findByFirstname(searchVar);
		}
		return null;
	}
	
	@RequestMapping("/findById/{id}")
	@Transactional
	@CrossOrigin
	public User searchById(@PathVariable(name="id") int tmp, @RequestHeader String id, @RequestHeader String authorization)
	{
		if(api.authenticate(id, authorization))
		{
			return userdao.findById(tmp);
		}
		return null;
	}
	
	@PostMapping("/edit-user")
	@Transactional
	@CrossOrigin
	public String editUser(@RequestBody User user, @RequestHeader String id, @RequestHeader String authorization)
	{
		if(api.authenticate(id, authorization))
		{
			userdao.editUser(user);
			return "Record of " + user.getFname() + " is updated successfully.";
		}
		return "Access Denied";
	}
	
	@GetMapping("/skillset-trend")
	@CrossOrigin 
	public HashMap<String,Integer> skillsetTrend()  // @RequestHeader String id, @RequestHeader String authorization
	{
		//if(api.authenticate(id, authorization))
		{
			return userdao.skillMap();
		}
		//return null;
	}
	
	@GetMapping("/gradyear-trend")
	@CrossOrigin
	public HashMap<String,Integer> gradyearTrend(@RequestHeader String id, @RequestHeader String authorization)
	{
		if(api.authenticate(id, authorization))
		{
			return userdao.gradyearMap();
		}
		return null;
	}
	
	@GetMapping("/graddiversity-trend")
	@CrossOrigin
	public HashMap<String,Integer> graddiversityTrend(@RequestHeader String id, @RequestHeader String authorization)
	{
		if(api.authenticate(id, authorization))
		{
			return userdao.graddiversityMap();
		}
		return null;
	}
	
	@GetMapping("/gradroles-trend")
	@CrossOrigin
	public HashMap<String,Integer> gradRolesMap(@RequestHeader String id, @RequestHeader String authorization)
	{
		if(api.authenticate(id, authorization))
		{
			return userdao.gradRolesMap();
		}
		return null;
	}
	
	@GetMapping("/gradfeedback-trend")
	@CrossOrigin
	public HashMap<String,Integer> gradFeedbackMap(@RequestHeader String id, @RequestHeader String authorization)
	{
		if(api.authenticate(id, authorization))
		{
			return userdao.gradFeedbackMap();
		}
		return null;
	}
}