package com.gradmanagement.project.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import com.gradmanagement.project.model.User;

class UserDAOTest {
	
	private UserDAO userdao;
	@BeforeEach
	void setUp() throws Exception {
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setUrl("jdbc:mysql://localhost/gradmanagement?allowPublicKeyRetrieval=true&useSSL=false");
		datasource.setUsername("root");
		datasource.setPassword("root");
		datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		userdao = new UserDAO(new JdbcTemplate(datasource));
	}
	
	@Test
	void testListGrad() {
		Iterable<User> listUser = userdao.listGrad();
		assertTrue(listUser.iterator().hasNext());
	}

	@Test
	void testSearchBySearchVar() {
		String searchVar = "Male";
		Iterable<User> listUser = userdao.searchBySearchVar(searchVar);
		assertTrue(listUser.iterator().hasNext());
	}
	
	@Test
	void testRegisterUser() {
		User user = new User(1000,"Rick","Morty","Male",23,"rick.morty@gmail.com","9999667788","street5","Manager",new Date(2019-05-03),"good","NSIT","JAVA","Mumbai");
		userdao.registerUser(user);
	}

	@Test
	void testEditUser() {
		int id = 999;
		User user = userdao.findById(id);
		user = new User(999,"Rick","Morty","Male",23,"rick.morty@gmail.com","9999667788","street5","SDE",new Date(2019-05-03),"good","NSIT","JAVA, SQL","Chennai");
		userdao.editUser(user);
	}
	
	@Test
	void testDeleteGrad() {
		int id = 1000;
		userdao.deleteGrad(id);
		assertThrows(EmptyResultDataAccessException.class,()->{userdao.findById(id);});
	}

	@Test
	void testFindById() {
		int id = 3;
		User user = userdao.findById(id);
		assertNotNull(user);
	}
	
	@Test
	void testSaveAndRetrieveLogs() throws IOException
	{
		userdao.saveLogs("Testing Log Retrieval Service");
		List<String> logs = userdao.retrieveLogs();
		assertFalse(logs.isEmpty());
	}
	
	@Test
	void testGradYearMap()
	{
		HashMap<String, Integer> map = userdao.gradyearMap();
		assertFalse(map.isEmpty());
	}
	
	@Test
	void testGraddiversityMap()
	{
		HashMap<String, Integer> map = userdao.graddiversityMap();
		assertFalse(map.isEmpty());
	}
	
	@Test
	void testskillMap()
	{
		HashMap<String, Integer> map = userdao.skillMap();
		assertFalse(map.isEmpty());
	}
	
	@Test
	void testGradFeedbackMap()
	{
		HashMap<String, Integer> map = userdao.gradFeedbackMap();
		assertFalse(map.isEmpty());
	}
	
	@Test
	void testgradRolesMap()
	{
		HashMap<String, Integer> map = userdao.gradRolesMap();
		assertFalse(map.isEmpty());
	}
}
