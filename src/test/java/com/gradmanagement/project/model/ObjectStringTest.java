package com.gradmanagement.project.model;

import org.junit.jupiter.api.Test;

class ObjectStringTest {

	ObjectString os = new ObjectString(1000,"Rick Morty rick.morty@gmail.com 9999999999 street5");

	@Test
	void testGetId() {
		int id = os.getId();
		assert(id==1000);
	}

	@Test
	void testSetId() {
		os.setId(1001);
		int id = os.getId();
		assert(id!=1000);
	}

	@Test
	void testGetString() {
		String str = os.getString();
		assert(str=="Rick Morty rick.morty@gmail.com 9999999999 street5");
	}

	@Test
	void testSetString() {
		os.setString("Rick Morty rick.morty@gmail.com 9999988888 street5");
		String str = os.getString();
		assert(str!="Rick Morty rick.morty@gmail.com 9999999999 street5");
	}

	@Test
	void testObjectStringIntString() {
		ObjectString new_os = new ObjectString(2000,"Rick Morty rick.morty@gmail.com 9999999999 street5");
		assert(new_os.getString()!=null);
	}

	@Test
	void testObjectString() {
		ObjectString new_os = new ObjectString();
		assert(new_os.getString()==null);
	}
}
