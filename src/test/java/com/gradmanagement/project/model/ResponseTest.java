package com.gradmanagement.project.model;

import org.junit.jupiter.api.Test;

class ResponseTest {

	Response res = new Response("Success","Data Stored");

	@Test
	void testResponse() {
		Response res_new = new Response();
		assert(res_new.getStatus()==null);
	}

	@Test
	void testGetStatus() {
		assert(res.getStatus()!=null);
	}

	@Test
	void testSetStatus() {
		res.setStatus("Failure");
		assert(res.getStatus()=="Failure");
	}

	@Test
	void testGetMessage() {
		assert(res.getMessage()!=null);
	}

	@Test
	void testSetMessage() {
		res.setMessage("Access Denied");
		assert(res.getMessage()!="Data Stored");
	}

	@Test
	void testResponseStringString() {
		Response res_new = new Response("Failure","Access Denied");
		assert(res.getStatus()!=res_new.getStatus());
	}

}
