package com.gradmanagement.project.model;

public class ObjectString {
	private int id;
	private String string;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}
	public ObjectString(int id, String string) {
		super();
		this.id = id;
		this.string = string;
	}
	public ObjectString() {
		super();
	}
	
}
