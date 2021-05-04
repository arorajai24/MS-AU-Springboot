package com.gradmanagement.project.security;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gradmanagement.project.model.AuthUser;

@Repository
public class ApiGateway {
	private JdbcTemplate jdbcobj;

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
			return true;
		else
			return false;
	}
	
}
