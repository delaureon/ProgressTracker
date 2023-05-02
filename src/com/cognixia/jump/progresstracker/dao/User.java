package com.cognixia.jump.progresstracker.dao;

public class User {

	
	// User attributes
	private int userId;
	private String username;
	private String password;
	private int roleType;
	
	public User() {
		this.userId = -1;
		this.username = null;
		this.password = null;
		this.roleType = -1;
	}
	
	public User(int userId, String username, String password, int roleType) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.roleType = roleType;
	}
	public User( String username,String password){
		this.password=password;
		this.username=username;
	}
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", roleType=" + roleType
				+ "]";
	}
	
	
	
}
