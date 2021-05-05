package com.gradmanagement.project.controller;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Date;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import com.gradmanagement.project.model.User;
import com.gradmanagement.project.security.ApiGateway;
import com.gradmanagement.project.service.UserDAO;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RESTController.class)
@WithMockUser
class RESTControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RESTController rest;
	
	@MockBean
	private ApiGateway api;
	
	@MockBean
	private UserDAO userdao;
	
	@BeforeEach
	void setUp() throws Exception {
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setUrl("jdbc:mysql://localhost/gradmanagement?allowPublicKeyRetrieval=true&useSSL=false");
		datasource.setUsername("root");
		datasource.setPassword("root");
		datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		userdao = Mockito.spy(new UserDAO(new JdbcTemplate(datasource)));
		api = new ApiGateway(new JdbcTemplate(datasource));
	}
	
	User mockUser1 = new User(2000,"Will","Smith","Male",23,"will.smith@gmail.com","9999667788","street5","Manager",new Date(2019-05-03),"good","NSIT","JAVA","Mumbai"); 
	User mockUser2 = new User(2001,"Will","Smith","Male",23,"will.smith@gmail.com","9999667788","street5","Manager",new Date(2019-05-03),"good","NSIT","JAVA","Mumbai"); 
	String templateUserJson = "{\"fname\":\"Will\",\"lname\":\"Smith\",\"gender\":\"Male\",\"fname\":\"Will\",\"age\":\"23\",\"email\":\"will.smith@gmail.com\",\"contact\":\"9999667788\",\"address\":\"street5\",\"role\":\"Manager\",\"date\":\"2019-05-03\",\"fname\":\"Will\",\"institution\":\"NSIT\",\"skillset\":\"JAVA\",\"Location\":\"Mumbai\"}";
	
	@Test
	void testAuthenticate()
	{
		String id="1000";
		String token="Test12345";
		boolean check = api.authenticate(id, token);
		System.out.print(check);
		assertTrue(check);
	}
	
	@Test
	void testListGrads() throws Exception {  
		
		ArrayList<User> list = new ArrayList<User>();
		list.add(mockUser1);
		Iterable<User> mockList = list;
		
		Mockito.doReturn(mockList).when(rest).listGrads("1000", "Test12345");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/allusers")
										.header("id", "1000")
										.header("authorization", "Test12345")
										.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "[{fname:Will, lname:Smith, gender:Male}]";

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}

	@Test
	void testRegister() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/save-user",mockUser2)
				.header("id", "1000")
				.header("authorization", "Test12345")
				.accept(MediaType.APPLICATION_JSON).content(templateUserJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(200, response.getStatus());

	}
	
	@Test
	void testRegister2() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/save-user",mockUser2)
				.accept(MediaType.APPLICATION_JSON).content(templateUserJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(400, response.getStatus());

	}

	@Test
	void testDeleteById() throws Exception {
		int id = 2000;
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/delete-user/"+id)
										.header("id", "1000")
										.header("authorization", "Test12345")
										.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(200, result.getResponse().getStatus());
	}
	
	@Test
	void testSearchBySearchVar() throws Exception {
		String searchVar = "Will";
		
		ArrayList<User> list = new ArrayList<User>();
		list.add(mockUser1);
		Iterable<User> mockList = list;
		
		Mockito.doReturn(mockList).when(rest).searchBySearchVar(searchVar, "1000", "Test12345");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/search/"+searchVar)
										.header("id", "1000")
										.header("authorization", "Test12345")
										.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "[{fname:Will, lname:Smith, gender:Male}]";

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}

	@Test
	void testSearchById() throws Exception {
		
		int id = 2000;
		
		Mockito.doReturn(mockUser1).when(rest).searchById(id,"1000", "Test12345");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/findById/"+id)
										.header("id", "1000")
										.header("authorization", "Test12345")
										.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "{fname:Will, lname:Smith, gender:Male}";

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}

	@Test
	void testEditUser() throws Exception {		

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/edit-user",mockUser1)
										.header("id", "1000")
										.header("authorization", "Test12345")
										.accept(MediaType.APPLICATION_JSON).content(templateUserJson)
										.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		assertEquals(200, result.getResponse().getStatus());
	}

	@Test
	void testSkillsetTrend() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/skillset-trend")
				.header("id", "1000")
				.header("authorization", "Test12345")
				.accept(MediaType.APPLICATION_JSON).content(templateUserJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
	}

	@Test
	void testGradyearTrend() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/skillset-trend")
				.header("id", "1000")
				.header("authorization", "Test12345")
				.accept(MediaType.APPLICATION_JSON).content(templateUserJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
	}

	@Test
	void testGraddiversityTrend() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/skillset-trend")
				.header("id", "1000")
				.header("authorization", "Test12345")
				.accept(MediaType.APPLICATION_JSON).content(templateUserJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
	}

	@Test
	void testGradRolesMap() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/skillset-trend")
				.header("id", "1000")
				.header("authorization", "Test12345")
				.accept(MediaType.APPLICATION_JSON).content(templateUserJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
	}

	@Test
	void testGradFeedbackMap() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/skillset-trend")
				.header("id", "1000")
				.header("authorization", "Test12345")
				.accept(MediaType.APPLICATION_JSON).content(templateUserJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
	}

	@Test
	void testSaveLogs() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/skillset-trend")
				.header("id", "1000")
				.header("authorization", "Test12345")
				.accept(MediaType.APPLICATION_JSON).content(templateUserJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
	}

	@Test
	void testRetrieveLogs() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/skillset-trend")
				.header("id", "1000")
				.header("authorization", "Test12345")
				.accept(MediaType.APPLICATION_JSON).content(templateUserJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
	}

}
