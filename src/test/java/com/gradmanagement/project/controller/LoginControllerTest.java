package com.gradmanagement.project.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.gradmanagement.project.model.AuthUser;
import com.gradmanagement.project.security.ApiGateway;
import com.gradmanagement.project.service.authUserDAO;

@RunWith(SpringRunner.class)
@WebMvcTest(value = LoginController.class)
@WithMockUser
class LoginControllerTest {

	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private LoginController login;
	
	@MockBean
	private ApiGateway api;
	
	@MockBean
	private authUserDAO authuserdao;
	
	@Before
	void setUp() throws Exception {
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setUrl("jdbc:mysql://localhost/gradmanagement?allowPublicKeyRetrieval=true&useSSL=false");
		datasource.setUsername("root");
		datasource.setPassword("root");
		datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		api = new ApiGateway(new JdbcTemplate(datasource));
		authuserdao = new authUserDAO(new JdbcTemplate(datasource));
		mockMvc = MockMvcBuilders.standaloneSetup(login).build();
	}
	
	AuthUser authUser = new AuthUser("1200","Rick.morty@example.com","Test","Test12345");
	String templateAuthUserJson = "{\"id\":\"1200\",\"email\":\"Rick.morty@example.com\",\"name\":\"Test\",\"authToken\":\"Test12345\"}";
	
	@Test
	void testSavesresponse() throws Exception{
		
		String uri = "/savesResponse";
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri,authUser)
			   .accept(MediaType.APPLICATION_JSON).content(templateAuthUserJson)
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   //String content = mvcResult.getResponse().getContentAsString();
	   
	}
	
	@Test
	void testRemoveResponse() throws Exception{
		
		Mockito.when(api.authenticate("1200", "Test12345")).thenReturn(true);
		String uri = "/removeResponse";
		   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
				   .header("id", "1200")
				   .header("authorization", "Test12345")
				   .accept(MediaType.APPLICATION_JSON)).andReturn();
		   
		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
	}
	
	@Test
	void testRemoveResponse2() throws Exception{

		Mockito.when(api.authenticate("1000", "Test12345")).thenReturn(false);
		String uri = "/removeResponse";
		   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
				   .header("id", "1000")
				   .header("authorization", "Test12345")
				   .accept(MediaType.APPLICATION_JSON)).andReturn();
		   
		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
	}

}
