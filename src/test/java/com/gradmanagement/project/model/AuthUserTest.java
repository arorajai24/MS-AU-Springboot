package com.gradmanagement.project.model;

import org.junit.jupiter.api.Test;

class AuthUserTest {

	AuthUser auth = new AuthUser("1000","Testing@example.com","Test","Test1234");
	
	@Test
	void testGetAuthToken() {
		String str="Test1234";
		String rts = auth.getAuthToken(); 
		assert(str.equals(rts));
	}
	
	@Test
	void testSetAuthToken() {
		String str="Test1234";
		auth.setAuthToken("Test12345");
		String rts = auth.getAuthToken(); 
		assert(!str.equals(rts));
	}
	
	@Test
	void testGetId() {
		String str="1000";
		String rts = auth.getId(); 
		assert(str.equals(rts));
	}
	
	@Test
	void testSetId() {
		String str="1000";
		auth.setId("2000");
		String rts = auth.getId(); 
		assert(!str.equals(rts));
	}
	
	@Test
	void testGetName() {
		String str="Test";
		String rts = auth.getName(); 
		assert(str.equals(rts));
	}
	
	@Test
	void testSetName() {
		String str="Test";
		auth.setName("Test12345");
		String rts = auth.getName(); 
		assert(!str.equals(rts));
	}
	
	@Test
	void testGetEmail() {
		String str="Testing@example.com";
		String rts = auth.getEmail(); 
		assert(str.equals(rts));
	}
	
	@Test
	void testSetEmail() {
		String str="Testing@example.com";
		auth.setAuthToken("Testing12345@example.com");
		String rts = auth.getAuthToken(); 
		assert(!str.equals(rts));
	}
	
}
