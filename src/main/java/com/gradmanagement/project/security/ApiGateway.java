package com.gradmanagement.project.security;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gradmanagement.project.controller.LoginController;
import com.gradmanagement.project.model.AuthUser;

import ch.qos.logback.classic.Logger;

@Repository
public class ApiGateway {
	private JdbcTemplate jdbcobj;
	Logger logger = (Logger) LoggerFactory.getLogger(LoginController.class);
	public ApiGateway(JdbcTemplate jdbcobj) {
		super();
		this.jdbcobj = jdbcobj;
	}
	
	public boolean authenticate(String id , String resToken){
		String sql = "SELECT * FROM authuser WHERE id=?";
		@SuppressWarnings("deprecation")
		AuthUser auth = jdbcobj.queryForObject(sql, new Object[]{id} ,BeanPropertyRowMapper.newInstance(AuthUser.class));
		String dbToken = auth.getAuthToken();
		if(dbToken.equals(resToken))
		{
			logger.info("User has been authenticated successfully.");
			return true;
		}
		else
		{	
			logger.info("Authentication failed.");
			return false;
		}
	}
	
}
