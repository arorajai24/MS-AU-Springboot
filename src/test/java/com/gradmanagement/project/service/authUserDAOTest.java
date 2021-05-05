package com.gradmanagement.project.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.gradmanagement.project.model.AuthUser;

class authUserDAOTest {

	@Autowired
	private authUserDAO authuserdao;
	
	@BeforeEach
	void setUp() throws Exception {
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setUrl("jdbc:mysql://localhost/gradmanagement?allowPublicKeyRetrieval=true&useSSL=false");
		datasource.setUsername("root");
		datasource.setPassword("root");
		datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		authuserdao = new authUserDAO(new JdbcTemplate(datasource));
	}
	
	@Test
	void testSaveAuthUser() {
		AuthUser authUser = new AuthUser("900","Rick.morty@example.com","Test","Test12345");
		authuserdao.saveAuthUser(authUser);
	}
	
	@Test
	void testSaveAuthUser2() {
		AuthUser authUser = new AuthUser("900","Rick.morty@example.com","Test","Test12345");
		authuserdao.saveAuthUser(authUser);
	}

	@Test
	void testRemoveResponse() {
		String id = "900";
		authuserdao.removeResponse(id);
	}

}
