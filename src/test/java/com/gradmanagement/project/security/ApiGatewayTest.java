package com.gradmanagement.project.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

class ApiGatewayTest {

	@Autowired
	ApiGateway api;
	
	@BeforeEach
	void setUp() throws Exception {
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setUrl("jdbc:mysql://localhost/gradmanagement?allowPublicKeyRetrieval=true&useSSL=false");
		datasource.setUsername("root");
		datasource.setPassword("root");
		datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		api = new ApiGateway(new JdbcTemplate(datasource));
	}

	@Test
	void testAuthenticate()
	{
		String id="1000";
		String token="Test12345";
		boolean check = api.authenticate(id, token);
		System.out.print(check);
		assertTrue(check);
	}

}
