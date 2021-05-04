package com.gradmanagement.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
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

import ch.qos.logback.classic.Logger;

@RestController
public class RESTController {

	Logger logger = (Logger) LoggerFactory.getLogger(RESTController.class);
	
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
			logger.info("Retrieving List of Candidates(View All)");
			return userdao.listGrad();
		}
		logger.info("Access denied of viewing");
		return null;
	}
	
	@RequestMapping("/delete-user/{id}")
	@CrossOrigin
	public void deleteById(@PathVariable(name="id") int tmp, @RequestHeader String id, @RequestHeader String authorization)
	{
		if(api.authenticate(id, authorization))
		{
			logger.info("Deleting Candidate of ID : " + tmp);
			userdao.deleteGrad(tmp);
			return;
		}
		logger.info("Access denied of deleting");
		return;
	}
	
	@PostMapping("/save-user")
	@Transactional
	@CrossOrigin
	public String register(@RequestBody User user, @RequestHeader String id, @RequestHeader String authorization)
	{
		if(api.authenticate(id, authorization))
		{
			logger.info("Registering Candidate by name : " + user.getFname()+" "+user.getLname());
			userdao.registerUser(user);
			return user.getFname() +" "+ user.getLname() + " has been registered successfully.";
		}
		logger.info("Access denied of registering");
		return "Access Denied";
	}
	
	@RequestMapping("/search/{fname}")
	@Transactional
	@CrossOrigin
	public Iterable<User> searchBySearchVar(@PathVariable(name="fname") String searchVar, @RequestHeader String id, @RequestHeader String authorization)
	{
		if(api.authenticate(id, authorization))
		{
			logger.info("Searching by word : " + searchVar);
			return userdao.searchBySearchVar(searchVar);
		}
		logger.info("Access denied of searching");
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
			logger.info("Editing candidate by id : " + user.getId()+" and name : "+user.getFname()+" "+user.getLname());
			userdao.editUser(user);
			return "Record of " + user.getFname() + " is updated successfully.";
		}
		logger.info("Access denied of editing candidate");
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
	
	@PostMapping("/api/logs")
	@CrossOrigin
	public void saveLogs(@RequestBody String str)
	{
		System.out.print(str);
	}
}