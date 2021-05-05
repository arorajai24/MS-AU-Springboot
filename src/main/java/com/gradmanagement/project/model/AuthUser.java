package com.gradmanagement.project.model;

public class AuthUser {
	String id;
	String email;
	String name;
	String authToken;
	
	public AuthUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AuthUser(String id, String email, String name, String authToken) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.authToken = authToken;
	}
	public String getEmail() {
		return email;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}	
}
