package com.gradmanagement.project.model;

import java.sql.Date;

import org.junit.jupiter.api.Test;

class UserTest {

	User user = new User(1000,"Rick","Morty","Male",23,"rick.morty@gmail.com","9999667788","street5","Manager",new Date(2019-05-03),"good","NSIT","JAVA","Mumbai");

	@Test
	void testUser() {
		User new_user = new User();
		assert(new_user.getAddress()==null);
	}

	@Test
	void testGetRole() {
		assert(user.getRole()!=null);
	}

	@Test
	void testSetRole() {
		user.setRole("SDE");
		assert(user.getRole()!="Manager");
	}

	@Test
	void testGetFeedback() {
		assert(user.getFeedback()!=null);
	}

	@Test
	void testSetFeedback() {
		user.setFeedback("Worst");
		assert(user.getFeedback()!="good");
	}

	@Test
	void testUserIntStringStringStringIntStringStringStringStringDateStringStringStringString() {
		User new_user = new User(2000,"Morty","Rick","Female",23,"rick.morty@gmail.com","9999966778","street5","Good",new Date(2019-05-03),"good","NSIT","JAVA","Mumbai");
		assert(new_user.getId()==2000);
	}

	@Test
	void testGetDate() {
		assert(user.getDate()!=null);
	}

	@Test
	void testSetDate() {
		user.setDate(new Date(2020-01-01));
		assert(user.getDate()!=new Date(2020-01-02));
	}

	@Test
	void testGetInstitution() {
		assert(user.getInstitution()!=null);
	}

	@Test
	void testSetInstitution() {
		user.setInstitution("DTU");
		assert(user.getInstitution()!="NSIT");
	}

	@Test
	void testGetSkillset() {
		assert(user.getSkillset()!=null);
	}

	@Test
	void testSetSkillset() {
		user.setSkillset("SQL");
		assert(user.getSkillset()!="JAVA");
	}

	@Test
	void testGetLocation() {
		assert(user.getLocation()!=null);
	}

	@Test
	void testSetLocation() {
		user.setLocation("SQL");
		assert(user.getLocation()!="JAVA");
	}

	@Test
	void testGetAge() {
		assert(user.getAge()==23);
	}

	@Test
	void testSetAge() {
		user.setAge(32);
		assert(user.getAge()!=23);
	}

	@Test
	void testGetGender() {
		assert(user.getGender()!=null);
	}

	@Test
	void testSetGender() {
		user.setGender("Other");
		assert(user.getGender()!="Male");
	}

	@Test
	void testGetId() {
		assert(user.getId()==1000);
	}

	@Test
	void testSetId() {
		user.setId(2000);
		assert(user.getId()!=1000);
	}

	@Test
	void testGetFname() {
		assert(user.getFname()!=null);
	}

	@Test
	void testSetFname() {
		user.setFname("Rick");
		assert(user.getFname()!="Morty");
	}

	@Test
	void testGetLname() {
		assert(user.getLname()!=null);
	}

	@Test
	void testSetLname() {
		user.setLname("Rick");
		assert(user.getLname()!="Morty");
	}

	@Test
	void testGetEmail() {
		assert(user.getEmail()!=null);
	}

	@Test
	void testSetEmail() {
		user.setEmail("testing@gmail.com");
		assert(user.getEmail()=="testing@gmail.com");
	}

	@Test
	void testGetContact() {
		assert(user.getContact()!=null);
	}

	@Test
	void testSetContact() {
		user.setContact("9999999999");
		assert(user.getContact()=="9999999999");
	}

	@Test
	void testGetAddress() {
		assert(user.getAddress()!=null);
	}

	@Test
	void testSetAddress() {
		user.setAddress("Street10");
		assert(user.getAddress()!="street5");
	}

}
