package com.gradmanagement.project.controller;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Date;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.MediaType;

import com.gradmanagement.project.model.User;
import com.gradmanagement.project.security.ApiGateway;
import com.gradmanagement.project.service.UserDAO;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RESTController.class)
@WithMockUser
class RESTControllerTest {
	
	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private RESTController rest;
	
	@MockBean
	private ApiGateway api;
	
	@MockBean
	private UserDAO userdao;

	@Before
	void setUp() throws Exception {
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setUrl("jdbc:mysql://localhost/gradmanagement?allowPublicKeyRetrieval=true&useSSL=false");
		datasource.setUsername("root");
		datasource.setPassword("root");
		datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		api = new ApiGateway(new JdbcTemplate(datasource));
		mockMvc = MockMvcBuilders.standaloneSetup(rest).build();
	}
	
	User mockUser1 = new User(2000,"Will","Smith","Male",23,"will.smith@gmail.com","9999667788","street5","Manager",new Date(2019-05-03),"good","NSIT","JAVA","Mumbai"); 
	User mockUser2 = new User(2001,"Will","Smith","Male",23,"will.smith@gmail.com","9999667788","street5","Manager",new Date(2019-05-03),"good","NSIT","JAVA","Mumbai"); 
	String templateUserJson = "{\"fname\":\"Will\",\"lname\":\"Smith\",\"gender\":\"Male\",\"fname\":\"Will\",\"age\":\"23\",\"email\":\"will.smith@gmail.com\",\"contact\":\"9999667788\",\"address\":\"street5\",\"role\":\"Manager\",\"date\":\"2019-05-03\",\"fname\":\"Will\",\"institution\":\"NSIT\",\"skillset\":\"JAVA\",\"Location\":\"Mumbai\"}";
	
	@Test
	void testListGrads() throws Exception {  
		
		ArrayList<User> list = new ArrayList<User>();
		list.add(mockUser1);
		Iterable<User> mockList = list;
		
		Mockito.doReturn(mockList).when(userdao).listGrad();
		Mockito.when(api.authenticate("1000", "Test12345")).thenReturn(true);
		
			String uri = "/allusers";
		   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
				   .header("id", "1000")
				   .header("authorization", "Test12345")
				   .accept(MediaType.APPLICATION_JSON)).andReturn();
		   
		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
		   String content = mvcResult.getResponse().getContentAsString();
		   System.out.print(content);
		   
		   String expected = "[{fname:Will, lname:Smith, gender:Male}]";

			JSONAssert.assertEquals(expected, mvcResult.getResponse()
					.getContentAsString(), false);

	}
	
	@Test
	void testListGradsElse() throws Exception {  
		
		ArrayList<User> list = new ArrayList<User>();
		list.add(mockUser1);
		Iterable<User> mockList = list;
		
		Mockito.doReturn(mockList).when(userdao).listGrad();
		Mockito.when(api.authenticate("2000", "Test12345")).thenReturn(false);
		
			String uri = "/allusers";
		   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
				   .header("id", "2000")
				   .header("authorization", "Test12345")
				   .accept(MediaType.APPLICATION_JSON)).andReturn();
		   
		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
		   String content = mvcResult.getResponse().getContentAsString();
		   System.out.print(content);

	}

	@Test
	void testRegister() throws Exception {

		Mockito.when(api.authenticate("1000", "Test12345")).thenReturn(true);
		String uri = "/save-user";
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri, mockUser1)
			   .header("id", "1000")
			   .header("authorization", "Test12345")
			   .accept(MediaType.APPLICATION_JSON).content(templateUserJson)
				.contentType(MediaType.APPLICATION_JSON)).andReturn();

	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   System.out.print(content);

	}
	
	@Test
	void testRegister2() throws Exception {

		Mockito.when(api.authenticate("2000", "Test12345")).thenReturn(false);
		String uri = "/save-user";
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri, mockUser1)
			   .header("id", "2000")
			   .header("authorization", "Test12345")
			   .accept(MediaType.APPLICATION_JSON).content(templateUserJson)
				.contentType(MediaType.APPLICATION_JSON)).andReturn();

	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   System.out.print(content);

	}

	@Test
	void testDeleteById() throws Exception {
		int id = 2000;
		Mockito.when(api.authenticate("1000", "Test12345")).thenReturn(true);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/delete-user/"+id)
				   .header("id", "1000")
				   .header("authorization", "Test12345")
				   .accept(MediaType.APPLICATION_JSON)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
		   String content = mvcResult.getResponse().getContentAsString();
		   System.out.print(content);
	}
	
	@Test
	void testDeleteById2() throws Exception {
		int id = 2000;
		Mockito.when(api.authenticate("2000", "Test12345")).thenReturn(false);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/delete-user/"+id)
				   .header("id", "2000")
				   .header("authorization", "Test12345")
				   .accept(MediaType.APPLICATION_JSON)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
		   String content = mvcResult.getResponse().getContentAsString();
		   System.out.print(content);
	}
	
	@Test
	void testSearchBySearchVar() throws Exception {
		String searchVar = "Will";
		Mockito.when(api.authenticate("1000", "Test12345")).thenReturn(true);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/search/"+searchVar)
				   .header("id", "1000")
				   .header("authorization", "Test12345")
				   .accept(MediaType.APPLICATION_JSON)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
		   String content = mvcResult.getResponse().getContentAsString();
		   System.out.print(content);

	}
	
	@Test
	void testSearchBySearchVar2() throws Exception {
		String searchVar = "Will";
		Mockito.when(api.authenticate("2000", "Test12345")).thenReturn(false);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/search/"+searchVar)
				   .header("id", "1000")
				   .header("authorization", "Test12345")
				   .accept(MediaType.APPLICATION_JSON)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
		   String content = mvcResult.getResponse().getContentAsString();
		   System.out.print(content);

	}

	@Test
	void testSearchById() throws Exception {
		int id = 2000;
		
		Mockito.doReturn(mockUser1).when(userdao).findById(id);
		Mockito.when(api.authenticate("1000", "Test12345")).thenReturn(true);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/findById/"+id)
				   .header("id", "1000")
				   .header("authorization", "Test12345")
				   .accept(MediaType.APPLICATION_JSON)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
		 
	}
	
	@Test
	void testSearchById2() throws Exception {
		int id = 2000;
		
		Mockito.doReturn(mockUser1).when(userdao).findById(id);
		Mockito.when(api.authenticate("2000", "Test12345")).thenReturn(false);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/findById/"+id)
				   .header("id", "2000")
				   .header("authorization", "Test12345")
				   .accept(MediaType.APPLICATION_JSON)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
		   String content = mvcResult.getResponse().getContentAsString();
		   System.out.print(content);
	
	}

	@Test
	void testEditUser() throws Exception {		

		Mockito.when(api.authenticate("1000", "Test12345")).thenReturn(true);
		
		String uri = "/edit-user";
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri, mockUser1)
			   .header("id", "1000")
			   .header("authorization", "Test12345")
			   .accept(MediaType.APPLICATION_JSON).content(templateUserJson)
				.contentType(MediaType.APPLICATION_JSON)).andReturn();

	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   System.out.print(content);
	}
	
	@Test
	void testEditUser2() throws Exception {		

		Mockito.when(api.authenticate("1000", "Test12345")).thenReturn(true);
		
		String uri = "/edit-user";
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri, mockUser1)
			   .header("id", "1000")
			   .header("authorization", "Test12345")
			   .accept(MediaType.APPLICATION_JSON).content(templateUserJson)
				.contentType(MediaType.APPLICATION_JSON)).andReturn();

	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   System.out.print(content);
	}

	@Test
	void testSkillsetTrend() throws Exception {
		Mockito.when(api.authenticate("1000", "Test12345")).thenReturn(true);
		String uri = "/skillset-trend";
		   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
				   .header("id", "1000")
				   .header("authorization", "Test12345")
				   .accept(MediaType.APPLICATION_JSON)).andReturn();

		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
		   String content = mvcResult.getResponse().getContentAsString();
		   assertTrue(content.length()!=0);
	}

	@Test
	void testSkillsetTrend2() throws Exception {
		Mockito.when(api.authenticate("2000", "Test12345")).thenReturn(false);
		String uri = "/skillset-trend";
		   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
				   .header("id", "2000")
				   .header("authorization", "Test12345")
				   .accept(MediaType.APPLICATION_JSON)).andReturn();

		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
//		   String content = mvcResult.getResponse().getContentAsString();
//		   assertTrue(content.length()!=0);
	}
	
	@Test
	void testGradyearTrend() throws Exception {
		Mockito.when(api.authenticate("1000", "Test12345")).thenReturn(true);
		String uri = "/gradyear-trend";
		   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
				   .header("id", "1000")
				   .header("authorization", "Test12345")
				   .accept(MediaType.APPLICATION_JSON)).andReturn();

		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
		   String content = mvcResult.getResponse().getContentAsString();
		   assertTrue(content.length()!=0);
	}

	@Test
	void testGradyearTrend2() throws Exception {
		Mockito.when(api.authenticate("2000", "Test12345")).thenReturn(false);
		String uri = "/gradyear-trend";
		   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
				   .header("id", "2000")
				   .header("authorization", "Test12345")
				   .accept(MediaType.APPLICATION_JSON)).andReturn();

		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
//		   String content = mvcResult.getResponse().getContentAsString();
//		   assertTrue(content.length()!=0);
	}
	
	@Test
	void testGraddiversityTrend() throws Exception {
		Mockito.when(api.authenticate("1000", "Test12345")).thenReturn(true);
		String uri = "/graddiversity-trend";
		   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
				   .header("id", "1000")
				   .header("authorization", "Test12345")
				   .accept(MediaType.APPLICATION_JSON)).andReturn();

		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
		   String content = mvcResult.getResponse().getContentAsString();
		   assertTrue(content.length()!=0);
	}
	
	@Test
	void testGraddiversityTrend2() throws Exception {
		Mockito.when(api.authenticate("2000", "Test12345")).thenReturn(false);
		String uri = "/graddiversity-trend";
		   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
				   .header("id", "2000")
				   .header("authorization", "Test12345")
				   .accept(MediaType.APPLICATION_JSON)).andReturn();

		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
	}

	@Test
	void testGradRolesMap() throws Exception {
		Mockito.when(api.authenticate("1000", "Test12345")).thenReturn(true);
		String uri = "/gradroles-trend";
		   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
				   .header("id", "1000")
				   .header("authorization", "Test12345")
				   .accept(MediaType.APPLICATION_JSON)).andReturn();

		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
		   String content = mvcResult.getResponse().getContentAsString();
		   assertTrue(content.length()!=0);
	}
	
	@Test
	void testGradRolesMap2() throws Exception {
		Mockito.when(api.authenticate("2000", "Test12345")).thenReturn(false);
		String uri = "/gradroles-trend";
		   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
				   .header("id", "2000")
				   .header("authorization", "Test12345")
				   .accept(MediaType.APPLICATION_JSON)).andReturn();

		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
	}

	@Test
	void testGradFeedbackMap() throws Exception {
		Mockito.when(api.authenticate("1000", "Test12345")).thenReturn(true);
		String uri = "/gradfeedback-trend";
		   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
				   .header("id", "1000")
				   .header("authorization", "Test12345")
				   .accept(MediaType.APPLICATION_JSON)).andReturn();

		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
		   String content = mvcResult.getResponse().getContentAsString();
		   assertTrue(content.length()!=0);
	}
	
	@Test
	void testGradFeedbackMap2() throws Exception {
		Mockito.when(api.authenticate("2000", "Test12345")).thenReturn(false);
		String uri = "/gradfeedback-trend";
		   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
				   .header("id", "2000")
				   .header("authorization", "Test12345")
				   .accept(MediaType.APPLICATION_JSON)).andReturn();

		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
	}

	@Test
	void testSaveLogs() throws Exception {
		String str = "logs testing...";
		Mockito.when(api.authenticate("1000", "Test12345")).thenReturn(true);
		
		String uri = "/api/logs";
		   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri,str)
				   .header("id", "1000")
				   .header("authorization", "Test12345")
				   .accept(MediaType.APPLICATION_JSON).content(templateUserJson)
					.contentType(MediaType.APPLICATION_JSON)).andReturn();

		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
	
	}

	@Test
	void testRetrieveLogs() throws Exception {
		Mockito.when(api.authenticate("1000", "Test12345")).thenReturn(true);
		
		String uri = "/dashboard/logs";
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
			   .header("id", "1000")
			   .header("authorization", "Test12345")
			   .accept(MediaType.APPLICATION_JSON)).andReturn();

	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   
	}

	@Test
	void testRetrieveLogs2() throws Exception {
		Mockito.when(api.authenticate("2000", "Test12345")).thenReturn(false);
		
		String uri = "/dashboard/logs";
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
			   .header("id", "2000")
			   .header("authorization", "Test12345")
			   .accept(MediaType.APPLICATION_JSON)).andReturn();

	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   //String content = mvcResult.getResponse().getContentAsString();
	   //assertTrue(content.length()!=0);
	}

}
