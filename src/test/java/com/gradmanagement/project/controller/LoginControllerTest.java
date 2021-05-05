package com.gradmanagement.project.controller;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.gradmanagement.project.model.AuthUser;
import com.gradmanagement.project.model.Response;
import com.gradmanagement.project.security.ApiGateway;
import com.gradmanagement.project.service.authUserDAO;

@RunWith(SpringRunner.class)
@WebMvcTest(value = LoginController.class)
@WithMockUser
class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LoginController login;
	
	@MockBean
	private ApiGateway api;
	
	@MockBean
	private authUserDAO authuserdao;
	
	@BeforeEach
	void setUp() throws Exception {
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setUrl("jdbc:mysql://localhost/gradmanagement?allowPublicKeyRetrieval=true&useSSL=false");
		datasource.setUsername("root");
		datasource.setPassword("root");
		datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		authuserdao = Mockito.spy(new authUserDAO(new JdbcTemplate(datasource)));
		api = new ApiGateway(new JdbcTemplate(datasource));
	}
	
	AuthUser authUser = new AuthUser("1200","Rick.morty@example.com","Test","Test12345");
	String templateAuthUserJson = "{\"id\":\"1200\",\"email\":\"Rick.morty@example.com\",\"name\":\"Test\",\"authToken\":\"Test12345\"}";
	
	@Test
	void testSavesresponse() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/savesResponse", authUser)
										.accept(MediaType.APPLICATION_JSON).content(templateAuthUserJson)
										.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());
	}
	
	@Test
	void testRemoveResponse() throws Exception{

		Response res = new Response("Success","Data Stored");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/removeResponse")
										.header("id", "1200")
										.header("authorization", "Test12345")
										.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "{'Success' , 'Data Stored'}";

		assertEquals(200, result.getResponse().getStatus());
	}
	
	@Test
	void testRemoveResponse2() throws Exception{

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/removeResponse")
										.header("id", "1500")
										.header("authorization", "Test12345")
										.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());
	}

}
